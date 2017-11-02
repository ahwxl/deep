package com.bplow.deep.stock.service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(locations = { "/applicationContext.xml","/applicationContext-myclient.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class QueryStockPriceRealTimeSerivceTest {

    @Autowired
    private QueryStockPriceRealTimeSerivce queryStockPriceRealTimeSerivce;
    
    @Test
    public void testQueryPrice(){
        
        queryStockPriceRealTimeSerivce.queryPrice("");
        
    }
    
}
