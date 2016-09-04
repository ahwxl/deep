package com.bplow.deep.base.pagination.pagesql.impl;


import org.apache.commons.lang3.StringUtils;

import com.bplow.deep.base.pagination.DBDialectType;
import com.bplow.deep.base.pagination.PageInfo;
import com.bplow.deep.base.pagination.pagesql.AbstractPageSql;

/**
 * Created by ajan on 2016/8/1.
 */
public class OraclePageSqlImpl extends AbstractPageSql {

    public OraclePageSqlImpl(){
        dbDialectType = DBDialectType.ORACLE.getName();
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
            int end = start + pageInfo.getPageSize();
            sb.append(" select * from ");
            sb.append("( ");
            sb.append(" select rownum row_num, wrap.* ");
            sb.append(" from (").append(sql).append(") wrap");
            sb.append(" where rownum <= ").append(end);
            sb.append(") t ");
            sb.append(" where t.row_num > ").append(start);
        }
        return sb.toString();
    }
}
