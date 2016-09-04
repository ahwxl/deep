package com.bplow.deep.base.pagination;

/**
 * Created by ajan on 2016/8/1.
 */
public enum DBDialectType {
    MYSQL("mysql", "com.bplow.deep.base.pagination.pagesql.impl.MySQLPageSqlImpl"),
    ORACLE("oracle", "com.bplow.deep.base.pagination.pagesql.impl.OraclePageSqlImpl");

    DBDialectType(String name, String pageSqlImplClass) {
        this.name = name;
        this.pageSqlImplClass = pageSqlImplClass;
    }

    private String name;
    private String pageSqlImplClass;

    public String getName() {
        return name;
    }

    public String getPageSqlImplClass() {
        return pageSqlImplClass;
    }
}
