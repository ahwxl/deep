package com.bplow.deep.stock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkCustomerWarn;

public interface SkCustomerWarnMapper {
    Page<SkCustomerWarn> queryForPage(SkCustomerWarn record);

    SkCustomerWarn selectByPrimaryKey(SkCustomerWarn record);
    
    List<SkCustomerWarn> selectCustomerWarns(@Param("userId")String userId,@Param("stockId")String stockId);

    int update(SkCustomerWarn record);

    int insert(SkCustomerWarn record);

    int deleteByPrimaryKey(String id);
}