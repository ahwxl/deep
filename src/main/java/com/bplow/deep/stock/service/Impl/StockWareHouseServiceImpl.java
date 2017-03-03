package com.bplow.deep.stock.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkWarehousePositon;
import com.bplow.deep.stock.service.StockWareHouseService;

@Service
public class StockWareHouseServiceImpl implements StockWareHouseService{
    
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Page<SkWarehousePositon> queryWarehouse(SkWarehousePositon position) {
        return null;
    }

    @Override
    public SkWarehousePositon createWarehouse(SkWarehousePositon position) {
        return null;
    }

    @Override
    public void deleleWarehouse(SkWarehousePositon position) {
    }

}
