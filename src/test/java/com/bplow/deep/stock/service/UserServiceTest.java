package com.bplow.deep.stock.service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bplow.deep.authority.User;
import com.bplow.deep.stock.domain.SysUser;

@ContextConfiguration(locations = { "/applicationContext.xml", "/spring/cxt-dao.xml",
                                   "/spring/cxt-redis.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class UserServiceTest {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Test
    public void testCreateUser() {

        for(int i=1;i<10000;i++){
            
        
        SysUser user = new SysUser();

        user.setEmail("javawxl@126.com");
        user.setUserId("gc"+i);
        user.setMobile("13681858154");
        user.setStatus("1");
        user.setPassword("123456");

        userService.createUser(user);
        
        }

    }

    @Test
    public void testDel() {
        SysUser user = new SysUser();
        user.setUserId("cxf");
        userService.deleteUser(user);

    }

    @Test
    public void testFindUser() {
        for (int i = 0; i < 100; i++) {
            User user = userService.findByUsername("cxf");
            System.out.println(user.getUserId());
        }

    }

}
