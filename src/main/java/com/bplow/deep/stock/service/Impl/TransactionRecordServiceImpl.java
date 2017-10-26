package com.bplow.deep.stock.service.Impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkTransactionRecord;
import com.bplow.deep.stock.mapper.SkTransactionRecordMapper;
import com.bplow.deep.stock.service.TransactionRecordService;

/**
 * 交易记录
 * 
 * @author wangxiaolei
 * @version $Id: TransactionRecordServiceImpl.java, v 0.1 2017年3月3日 下午12:40:25 wangxiaolei Exp $
 */

@Service
public class TransactionRecordServiceImpl implements TransactionRecordService{
    
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SkTransactionRecordMapper transactionRecordMapper;
    
    
    @Override
    public SkTransactionRecord createTransactionRecord(SkTransactionRecord record) {
        
    	record.setId(UUID.randomUUID().toString().replace("-", ""));
        transactionRecordMapper.insert(record);
        
        return record;
    }

    @Override
    public void deleteTransactionRecord(SkTransactionRecord record) {
        
        transactionRecordMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public Page<SkTransactionRecord> queryForPage(SkTransactionRecord record) {
        
        Page<SkTransactionRecord> page = transactionRecordMapper.queryForPage(record);
        
        return page;
    }

    @Override
    public SkTransactionRecord querySkTransactionRecord(SkTransactionRecord record) {
        
        SkTransactionRecord skTransactionRecord = transactionRecordMapper.selectByPrimaryKey(record.getId());
        
        return skTransactionRecord;
    }

    @Override
    public void updateTransactionRecord(SkTransactionRecord record) {
        
        transactionRecordMapper.update(record);
        
    }

}
