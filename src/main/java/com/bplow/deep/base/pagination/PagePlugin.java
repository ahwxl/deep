package com.bplow.deep.base.pagination;

import com.bplow.deep.base.pagination.pagesql.AbstractPageSql;
import com.bplow.deep.base.pagination.pagesql.PageSql;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ajan on 2016/7/7.
 */
@Intercepts(value = {@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PagePlugin implements Interceptor {

    private static final Logger LOGGER = Logger.getLogger(PagePlugin.class);

    private static final Map<String, String> dbDialectPageSqlRegistry = new HashMap<String, String>();
    private static final Map<String, AbstractPageSql> dbDialectPageSqlImplRegistry = new HashMap<String, AbstractPageSql>();

    private static String pageMatcher = PageConstant.PAGE_MATCHER_DEFAULT;
    private static String pageDialect = null;

    public PagePlugin() {
        registerDefaultDBDialectPageSqlImplClass();
    }

    public PagePlugin(String pageMatcher) {
        this(pageMatcher, null, null);
    }

    public PagePlugin(String pageMatcher, String pageDialect, String dialectPageSqlImplClass) {
        this();
        if (StringUtils.isNotEmpty(pageMatcher)) {
            this.pageMatcher = pageMatcher;
        }
        if (StringUtils.isNotEmpty(pageDialect)) {
            this.pageDialect = pageDialect;
        }
        registerDBDialectPageSqlImplClass(pageDialect, dialectPageSqlImplClass);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        LOGGER.debug("intercept -> invocation.target : " + invocation.getTarget());

        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);

            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue(PageConstant.DELEGATE_MAPPEDSTATEMENT);
            String sqlId = mappedStatement.getId();

            // 匹配约定的分页sqlId
            if (sqlId.matches(this.pageMatcher)) {

                LOGGER.debug("Found matched sql, the id is [" + sqlId + "]");

                BoundSql boundSql = statementHandler.getBoundSql();
                
                // 解析分页参数
                PageInfo pageInfo = null;
                Object parameterObject = boundSql.getParameterObject();
                /*
                 * 这里其实不用判断，所有的参数最后都被放入到一个Map中
                 * 具体可以看MapperMethod$ParamMap继承于HashMap
                 */
                LOGGER.debug("parameterObject " + parameterObject);
                if (parameterObject instanceof PageInfo) {
                    pageInfo = (PageInfo) parameterObject;
                } else if (parameterObject instanceof Map<?, ?>) {
                    Map<?, ?> parameters = (Map<?, ?>) parameterObject;
                    if (parameters.containsKey(PageConstant.PAGE_PARAMETER_MATCHER_DEFAULT)) {
                        pageInfo = (PageInfo) parameters.get(PageConstant.PAGE_PARAMETER_MATCHER_DEFAULT);
                    } else {
                        for (Map.Entry<?, ?> entry : parameters.entrySet()) {
                            if (entry.getValue() instanceof PageInfo) {
                                pageInfo = (PageInfo) entry.getValue();
                                break;
                            }
                        }
                    }
                }

                if (pageInfo == null) {
                    LOGGER.warn("PageInfo of " + sqlId + " not found.");
                } else {
                    // 获取数据库方言
                    Configuration configuration = (Configuration) metaStatementHandler.getValue(PageConstant.DELEGATE_CONFIGURATION);
                    String dialect = configuration.getVariables().getProperty(PageConstant.DB_DIALECT_MATCHER);
                    LOGGER.debug("Found dialect from configuration : " + dialect);
                    if (StringUtils.isBlank(dialect)) {
                        dialect = DBDialectType.MYSQL.getName();
                    }
                    if (StringUtils.isNotEmpty(pageDialect)) {
                        dialect = pageDialect;
                        LOGGER.debug("Found custom set dialect : " + pageDialect);
                    }

                    String sql = boundSql.getSql();
                    PageSql pageSqlInstance = getDBDialectPageSqlImplInstance(dialect);

                    if (pageSqlInstance != null) {
                        String pageSql = pageSqlInstance.getPageSql(sql, pageInfo);

                        LOGGER.debug("pagesql => " + pageSql);

                        if (StringUtils.isNotEmpty(pageSql)) {
                            //替换分页sql
                            metaStatementHandler.setValue(PageConstant.DELEGATE_BOUNDSQL_SQL, pageSql);

                            ParameterHandler parameterHandler = statementHandler.getParameterHandler();

                            int totalCount = executeQueryCount((Connection) invocation.getArgs()[0], parameterHandler, sql);
                            
                            Object ogj = invocation.getTarget();

                            pageInfo.setTotalCount(totalCount);
                        }
                    } else {
                        LOGGER.error("PageSql implement of " + dialect + " not found. ");
                    }
                }
            }
        }
        return invocation.proceed();
    }

    private int executeQueryCount(Connection connection, ParameterHandler parameterHandler, String sql) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            // 查询总记录数
            String countSql = "select count(1) from ( " + sql + " ) count_clause ";
            preparedStatement = connection.prepareStatement(countSql);
            /*
             * 查询总记录数的sql是包装了原先的sql语句，
             * 相对于原先的sql语句，在参数，数序，以及对应关系上都没有发生改变，
             * 直接使用已有的ParameterHandler进行参数设置，传入的sql语句不同
             */
            parameterHandler.setParameters(preparedStatement);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            LOGGER.error("execute query count error, " + e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return 0;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        LOGGER.debug("Found properties set : " + properties);

        String pageMatcher = properties.getProperty(PageConstant.PAGE_MATCHER_PROPERTY_KEY, PageConstant.PAGE_MATCHER_DEFAULT);
        if (StringUtils.isNotEmpty(pageMatcher)) {
            this.pageMatcher = pageMatcher;
        }

        String pageDialect = properties.getProperty(PageConstant.DB_DIALECT_MATCHER, null);
        if (StringUtils.isNotEmpty(pageDialect)) {
            this.pageDialect = pageDialect;
        }

        String dbDialectPageSqlImplClass = properties.getProperty(PageConstant.DB_PAGESQL_CLASS_PROPERTY_KEY, null);
        registerDBDialectPageSqlImplClass(pageDialect, dbDialectPageSqlImplClass);
    }

    private static void registerDefaultDBDialectPageSqlImplClass() {
        registerDBDialectPageSqlImplClass(DBDialectType.MYSQL.getName(), DBDialectType.MYSQL.getPageSqlImplClass());
        registerDBDialectPageSqlImplClass(DBDialectType.ORACLE.getName(), DBDialectType.ORACLE.getPageSqlImplClass());
    }

    private static void registerDBDialectPageSqlImplClass(String dbDialectType, String dbDialectPageSqlImplClass) {
        if (StringUtils.isNotEmpty(dbDialectType) && StringUtils.isNotEmpty(dbDialectPageSqlImplClass)) {
            dbDialectPageSqlRegistry.put(dbDialectType, dbDialectPageSqlImplClass);
        }
    }

    private String getDBDialectPageSqlImplClass(String dbDialectType) {
        return dbDialectPageSqlRegistry.get(dbDialectType);
    }

    private AbstractPageSql getDBDialectPageSqlImplInstance(String dbDialectType) {
        AbstractPageSql pageSql = this.dbDialectPageSqlImplRegistry.get(dbDialectType);
        if (pageSql == null) {
            String clazName = getDBDialectPageSqlImplClass(dbDialectType);
            try {
                Class<AbstractPageSql> pageSqlClaz = (Class<AbstractPageSql>) Class.forName(clazName);
                if (pageSqlClaz != null) {
                    pageSql = pageSqlClaz.newInstance();
                }
            } catch (Exception e) {
                LOGGER.error("Reflect new instance error , " + e.getMessage());
            }
            if (pageSql != null) {
                pageSql.setDbDialectType(dbDialectType);
                this.dbDialectPageSqlImplRegistry.put(dbDialectType, pageSql);
            }
        }
        return pageSql;
    }

}

