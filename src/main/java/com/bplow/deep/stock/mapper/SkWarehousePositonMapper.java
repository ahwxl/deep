package com.bplow.deep.stock.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkWarehousePositon;

public interface SkWarehousePositonMapper {
    Page<SkWarehousePositon> queryForPage(SkWarehousePositon record);

    SkWarehousePositon selectByPrimaryKey(String stockId);

    int update(SkWarehousePositon record);

    int insert(SkWarehousePositon record);

    int deleteByPrimaryKey(String stockId);
}