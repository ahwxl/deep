package com.bplow.deep.base;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;

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

import com.bplow.deep.base.classload.MyPrinter2;
import com.bplow.deep.base.utils.CompileMemery;
import com.bplow.deep.base.utils.RefreshBeanService;


@ContextConfiguration(locations = { "/applicationContext.xml","/applicationContext-myclient.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class CompileMemeryTest {
    
    private Logger          logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CompileMemery compileMemery;
    
    @Autowired
    private RefreshBeanService refreshBeanService;
    
    @Test
    public void testCompile(){
        
        try {
            compileMemery.comppile("MyPrinter2");
        } catch (ClassNotFoundException e) {
            logger.error("", e);
        } catch (NoSuchMethodException e) {
            logger.error("", e);
        } catch (SecurityException e) {
            logger.error("", e);
        } catch (IllegalAccessException e) {
            logger.error("", e);
        } catch (IllegalArgumentException e) {
            logger.error("", e);
        } catch (InvocationTargetException e) {
            logger.error("", e);
        } catch (InstantiationException e) {
            logger.error("", e);
        }
        
    }
    
    @Test
    public void testFresh() throws Exception{
        
        refreshBeanService.fresh("MyPrinter2");
        
    }
    
    @Test
    public void testBeanInfo() throws Exception{
        
        Class beanClass = MyPrinter2.class;
        
        System.out.println(beanClass.getClassLoader());
        
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass, Introspector.IGNORE_ALL_BEANINFO);
        
        System.out.println(beanInfo);
    }

}
