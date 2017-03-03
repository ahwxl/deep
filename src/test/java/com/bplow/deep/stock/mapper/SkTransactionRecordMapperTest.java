package com.bplow.deep.stock.mapper;

import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkTransactionRecord;

@ContextConfiguration(locations = { "/applicationContext.xml","/applicationContext-myclient.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class SkTransactionRecordMapperTest {
    
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private SkTransactionRecordMapper skTransactionRecordMapper;
    
    @Test
    public void testInsert(){
        for(int i=0;i<11;i++){
            SkTransactionRecord record = new SkTransactionRecord();
            record.setId(UUID.randomUUID().toString().replace("-",""));
            record.setStockId("000600");
            record.setStockName("建投能源");
            record.setAmount(1600l);
            record.setPrice(11.916);
            record.setGmtCreate(new Date());
            int rows =skTransactionRecordMapper.insert(record);
            logger.info("插入记录:{}",rows);
        }
    }
    
    @Test
    public void testQuery(){
        SkTransactionRecord record = new SkTransactionRecord();
        
       Page<SkTransactionRecord> page = skTransactionRecordMapper.queryForPage(record);
        
       
       logger.info("插入记录数:{}",page);
    }
    
    @Test
    public void testSelectByOne(){
    	
    	SkTransactionRecord record =	skTransactionRecordMapper.selectByPrimaryKey("12637e57426d46bf85b08ba534677482");
    	
    	logger.info("查询结果:{}",record);
    }

}
