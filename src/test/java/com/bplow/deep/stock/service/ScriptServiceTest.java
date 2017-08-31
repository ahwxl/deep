package com.bplow.deep.stock.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
public class ScriptServiceTest {
    
    @Autowired
    private ScriptService scripteService;
    
    @Test
    public void testScriptExecute(){
        
        Map<String,Serializable> param = new HashMap<String, Serializable>();
        
        param.put("a", 1);
        param.put("b", 2);
        
        String script = "a>b";
        
        scripteService.executeScripte(script, param);
        
        
    }

}
