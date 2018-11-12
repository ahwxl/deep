package com.bplow.deep.sysmng.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@ContextConfiguration(locations = { "/applicationContext.xml","/spring/cxt-email.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class SendMailServiceTest {

    @Autowired
    SendMailService sendMailService;
    
    
    @Test
    public void testSendMail(){
        Map map = new HashMap();
        map.put("toEmail", "www@126.com");
        map.put("url", "www.baidu.com");
        
       String content = sendMailService.getEmailCnt("emailcxt.vm", map);
        
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("tenement_admin@163.com");
        msg.setSubject("[执行结果通知]");
        msg.setText(content);
        msg.setTo(new String[]{"tenement_admin2222@163.com"});
        
        sendMailService.sendMail(msg);
        
    }
    
    
    
}
