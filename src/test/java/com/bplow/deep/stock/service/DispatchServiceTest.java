package com.bplow.deep.stock.service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 任务分发
 * 
 * @author wangxiaolei
 * @version $Id: DispatchServiceTest.java, v 0.1 2017年11月2日 下午5:47:29 wangxiaolei Exp $
 */

@ContextConfiguration(locations = { "/applicationContext.xml","/applicationContext-myclient.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class DispatchServiceTest {
    
    @Autowired
    private DispatchService dispatchService;

    @Test
    public void testDispatch(){
        dispatchService.dispatchAction();
    }
}
