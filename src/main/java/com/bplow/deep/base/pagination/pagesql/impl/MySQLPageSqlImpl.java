package com.bplow.deep.base.pagination.pagesql.impl;

import com.bplow.deep.base.pagination.DBDialectType;
import com.bplow.deep.base.pagination.PageInfo;
import com.bplow.deep.base.pagination.pagesql.AbstractPageSql;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ajan on 2016/8/1.
 */
public class MySQLPageSqlImpl extends AbstractPageSql {

    public MySQLPageSqlImpl(){
        dbDialectType = DBDialectType.MYSQL.getName();
    }

    @Override
    public String getPageSql(String sql, PageInfo pageInfo) {
        StringBuilder sb = new StringBuilder("");
        if (StringUtils.isNotBlank(sql) && pageInfo != null) {
            if(pageInfo.getPageNo() < 1){
                pageInfo.setPageNo(1);
            }
            if(pageInfo.getPageSize() <= 0){
                pageInfo.setPageSize(10);
            }
            int start = (pageInfo.getPageNo() - 1) * pageInfo.getPageSize();
            start = pageInfo.getiDisplayStart();
            int pageSize = pageInfo.getPageSize();
            sb.append(sql).append(" limit ").append(start).append(",").append(pageSize).append(" ");
        }
        return sb.toString();
    }
}
