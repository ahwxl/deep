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

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

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
	
	 private Logger          log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BmpServiceImpl bpmService;
	
	@Test
	public void deployTest(){
		bpmService.deploy("bpm/countersign.bpmn20.xml");

	}
	
	@Test
	public void testDeployByInputstream(){
		InputStream is = this.getClass().getResourceAsStream("/bpm/leave.bpmn20.xml");
		bpmService.deploy("leave.bpmn20.xml", is);
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
	public void testGetprocessDef(){
		
		List list = bpmService.getProcesDef("");
		log.info("{}",list);
	}
	
	@Test
	public void testGetProcessList(){
		
		List list = bpmService.getProcessList(0, 10);
		
		log.info("{}",list);
	}
	
	@Test
	public void testGetProcess(){
		
		ProcessInstance obj = bpmService.getProcessInstance("25001");
		
		log.info("{}",obj);
	}

}
