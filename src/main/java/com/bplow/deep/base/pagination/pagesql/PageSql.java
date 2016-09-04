package com.bplow.deep.base.pagination.pagesql;

import com.bplow.deep.base.pagination.PageInfo;

/**
 * Created by ajan on 2016/8/1.
 */
public interface PageSql {
    public String getPageSql(String sql, PageInfo pageInfo);
}
