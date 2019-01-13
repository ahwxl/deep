package com.bplow.deep.dynamicBeanLoad;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bplow.deep.base.groovy.RequestMessageParse;
import com.bplow.deep.dynamicBeanLoad.domain.GroovyDynamicBean;


@ContextConfiguration(locations = { "/applicationContext.xml","/applicationContext-myclient.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("development")
public class DynamicBeanReaderImplTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	DynamicBeanReader dynamicBeanReader;
	
	@Test
	public void loadBeanTest(){
		GroovyDynamicBean groovyBean = new GroovyDynamicBean("parse_scripte");
		
		dynamicBeanReader.loadBean(groovyBean);
		
		RequestMessageParse bean = (RequestMessageParse)applicationContext.getBean("parse_scripte");
		bean.parseText("");
		
		dynamicBeanReader.loadBean(groovyBean);
		bean = (RequestMessageParse)applicationContext.getBean("parse_scripte");
        bean.parseText("");
        
        System.out.println("");
		
	}

}
