/**
 * www.bplow.com
 */
package com.bplow.deep.bpm;

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
	
	@Test
	public void deployTest(){
		bpmService.deploy("");
		//InputStream is = this.getClass().getResourceAsStream("/bpm/vacationRequest.bpmn20.xml");
		//bpmService.deploy("vacationRequest", is);
	}
	
	@Test
	public void startProcessTest(){
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "Kermit");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");
		
		bpmService.startProcessById("vacationRequest:1:20003",variables);
	}
	
	@Test
	public void completeTask(){
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("vacationApproved", "false");
		taskVariables.put("managerMotivation", "We have a tight deadline!");
		
		
	}

}
