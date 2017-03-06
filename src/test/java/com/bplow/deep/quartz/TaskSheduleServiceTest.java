/**
 * www.bplow.com
 */
package com.bplow.deep.quartz;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月24日 下午10:11:17
 */
@ContextConfiguration(locations = { "/applicationContext.xml","/applicationContext-myclient.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class TaskSheduleServiceTest {
	
	@Autowired
	private TaskSheduleService taskSheduleService;
	
	@Test
	public void testCreateCronTask() throws SchedulerException, ClassNotFoundException{
		
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		
		map.put("taskId", "wxl");
		map.put("stockId", "sh600078");
		
		taskSheduleService.createCronTask("group1","job1","trigger1","0/3 * * * * ?",null,map);
		
		System.out.println("");
	}
	
	@Test
	public void testDel() throws SchedulerException{
		
		taskSheduleService.deleteCronTask("job1", "group1");
		
		System.out.println("");
	}

}
