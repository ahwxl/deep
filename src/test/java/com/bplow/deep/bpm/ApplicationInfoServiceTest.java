/**
 * www.bplow.com
 */
package com.bplow.deep.bpm;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.maintain.domain.AutoAppInfo;
import com.bplow.deep.maintain.mapper.AutoAppInfoMapper;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2016年9月11日 上午11:36:57
 */
@ContextConfiguration(locations = { "/applicationContext.xml","/applicationContext-myclient.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class ApplicationInfoServiceTest {

	@Autowired
	private AutoAppInfoMapper autoAppInfoMapper;
	
	@Test
	public void testInsert(){
		for(int i=0;i<10;i++){
			AutoAppInfo record = new AutoAppInfo();
			record.setAppCode("paycoreA"+i);
			record.setAppName("支付核心S"+i);
			autoAppInfoMapper.insert(record);
		}
	}
	
	@Test
	public void testquery(){
		AutoAppInfo record = new AutoAppInfo();
		record.setPageSize(10);
		record.setPageNo(1);
		record.setiDisplayStart(1);
		Page<AutoAppInfo> list = autoAppInfoMapper.queryForPage(record);
		
		System.out.println(list);
	}
	
	@Test
	public void testqueryOneRecord(){
		
		AutoAppInfo vo = autoAppInfoMapper.selectByPrimaryKey(1);
		
		System.out.println(vo.getGmtCreate());
	}
	
	@Test
	public void testDelete(){
		AutoAppInfo record = new AutoAppInfo();
		autoAppInfoMapper.delete(1);
	}
	
	@Test
	public void testUpdate(){
		AutoAppInfo record = new AutoAppInfo();
		record.setAppCode("fininfo");
		record.setId(10);
		
		autoAppInfoMapper.update(record);
	}
	
}
