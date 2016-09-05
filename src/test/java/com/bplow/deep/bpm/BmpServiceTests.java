/**
 * www.bplow.com
 */
package com.bplow.deep.bpm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.AutoAppInfo;
import com.bplow.deep.bpm.mapper.AutoAppInfoMapper;
import com.bplow.deep.bpm.service.impl.BmpServiceImpl;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2016年7月9日 上午10:53:58
 */
@ContextConfiguration(locations = { "/applicationContext.xml","/applicationContext-myclient.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class BmpServiceTests {
	
	@Autowired
	private BmpServiceImpl bpmService;
	
	@Autowired
	private AutoAppInfoMapper autoAppInfoMapper;
	
	@Test
	public void deployTest(){
		bpmService.deploy("bpm/countersign.bpmn20.xml");
//		InputStream is = this.getClass().getResourceAsStream("/bpm/vacationRequest.bpmn20.xml");
//		bpmService.deploy("vacationRequest", is);
	}
	
	@Test
	public void startProcessTest(){
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "Kermit");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");
		
		List<String> countersigns = new ArrayList<String>();
		for(int i =0;i<5;i++){
		    //CounterSign countersign = new CounterSign();
		    //countersign.setAssignee("user"+i);
		    //countersigns.add(countersign);
		    countersigns.add("user"+i);
		}
		
		variables.put("assigneeList", countersigns);
		
		//bpmService.startProcessById("vacationRequest:1:2503",variables);
		bpmService.startProcessById("countersign:5:52504",variables);
	}
	
	@Test
	public void completeTaskTest(){
	    String processId = "";
	    String taskId    = "5008";
	    taskId    = "55022";
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		//taskVariables.put("vacationApproved", "false");
		//taskVariables.put("managerMotivation", "We have a tight deadline!");
		
		taskVariables.put("resendRequest", "true");
		
		bpmService.completeTask(processId, taskId, taskVariables);
	}
	
	@Test
	public void getImageInputStreamTest() throws FileNotFoundException, IOException{
		
		InputStream in = bpmService.getImageInputStream("vacationRequest:1:2503");
		File file = new File("c:/log/bpm.jpg");
		IOUtils.copy(in, new FileOutputStream(file));
		in.close();
	}
	
	@Test
	public void getImageInputStreamByIdTest() throws FileNotFoundException, IOException{
		
		InputStream in = bpmService.getImageInputStreamById("countersign:2:25004");
		File file = new File("d:/logs/sign2.jpg");
		IOUtils.copy(in, new FileOutputStream(file));
		in.close();
	}
	
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
