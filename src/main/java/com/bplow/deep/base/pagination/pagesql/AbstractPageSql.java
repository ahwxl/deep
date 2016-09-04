package com.bplow.deep.base.pagination.pagesql;

import com.bplow.deep.base.pagination.PageInfo;

/**
 * Created by ajan on 2016/8/1.
 * 实现类必须提供默认的构造函数
 */
public abstract class AbstractPageSql implements PageSql{

    protected String dbDialectType;

    @Override
    public abstract String getPageSql(String sql, PageInfo pageInfo);

    public String getDbDialectType() {
        return dbDialectType;
    }

    public void setDbDialectType(String dbDialectType) {
        this.dbDialectType = dbDialectType;
    }
}
