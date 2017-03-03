package com.bplow.deep.stock.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkCustomerWarn;
import com.bplow.deep.stock.mapper.SkCustomerWarnMapper;
import com.bplow.deep.stock.service.CustomerWarnService;

@Service
public class CustomerWarnServiceImpl implements CustomerWarnService{

    Logger logger =LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    SkCustomerWarnMapper skCustomerWarnMapper;
    
    @Override
    public Page<SkCustomerWarn> queryCustomerWarns(SkCustomerWarn warn) {
        
        Page<SkCustomerWarn> page = skCustomerWarnMapper.queryForPage(warn);
        
        
        return page;
    }

    @Override
    public SkCustomerWarn queryCustomerWarn(SkCustomerWarn warn) {
        
        SkCustomerWarn customerWarn = skCustomerWarnMapper.selectByPrimaryKey(warn.getId());
        
        return customerWarn;
    }

    @Override
    public int createCustomerWarn(SkCustomerWarn warn) {
        
        skCustomerWarnMapper.insert(warn);
        
        return 0;
    }

    @Override
    public int deleteCustomerWarn(SkCustomerWarn warn) {
        
        skCustomerWarnMapper.deleteByPrimaryKey(warn.getId());
        
        return 0;
    }

    @Override
    public int updateCustomerWarn(SkCustomerWarn warn) {
        
        
        return 0;
    }

}
