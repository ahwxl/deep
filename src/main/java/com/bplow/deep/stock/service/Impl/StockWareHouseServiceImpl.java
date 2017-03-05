package com.bplow.deep.stock.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkWarehousePositon;
import com.bplow.deep.stock.mapper.SkWarehousePositonMapper;
import com.bplow.deep.stock.mapper.SkWarnRuleMapper;
import com.bplow.deep.stock.service.StockWareHouseService;

@Service
public class StockWareHouseServiceImpl implements StockWareHouseService{
    
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    SkWarehousePositonMapper skWarehousePositonMapper;

    @Override
    public Page<SkWarehousePositon> queryWarehouse(SkWarehousePositon position) {
    	
    	Page<SkWarehousePositon> page = skWarehousePositonMapper.queryForPage(position);
    	
        return page;
    }

    @Override
    public SkWarehousePositon createWarehouse(SkWarehousePositon position) {
    	
    	skWarehousePositonMapper.insert(position);
    	
        return position;
    }

    @Override
    public void deleleWarehouse(SkWarehousePositon position) {
    	
    	skWarehousePositonMapper.deleteByPrimaryKey(position.getStockId());
    	
    }

}
