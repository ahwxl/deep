package com.bplow.deep.stock.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkCustomerWarn;

public interface SkCustomerWarnMapper {
    Page<SkCustomerWarn> queryForPage(SkCustomerWarn record);

    SkCustomerWarn selectByPrimaryKey(String id);

    int update(SkCustomerWarn record);

    int insert(SkCustomerWarn record);

    int deleteByPrimaryKey(String id);
}