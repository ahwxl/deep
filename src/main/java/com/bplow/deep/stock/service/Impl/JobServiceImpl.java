/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.quartz.CronTrigger;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.quartz.TaskSheduleService;
import com.bplow.deep.stock.domain.SkScheduleTask;
import com.bplow.deep.stock.mapper.SkScheduleTaskMapper;
import com.bplow.deep.stock.service.JobService;
import com.bplow.deep.stock.service.SwitchDayService;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月26日 下午9:29:10
 */
@Service
public class JobServiceImpl implements JobService{
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SkScheduleTaskMapper skScheduleTaskMapper;
	
	@Autowired
	private TaskSheduleService taskSheduleService;
	
	@Autowired
	private SwitchDayService switchDayService;

	@Override
	public void createJob(SkScheduleTask task) {
		
		Map<String,Serializable> parament = new HashMap<String,Serializable>();
		
		parament.put("userId", task.getUserId());
		parament.put("stockId", task.getStockId());
		
		try {
			switchDayService.switchDay();
			
			CronTrigger trigger = taskSheduleService.createCronTask(task.getGroupId(), task.getJobId(), task.getTriggerName(), task.getCron(),task.getJobBean(), parament);
		    
			task.setId(UUID.randomUUID().toString().replace("-", ""));
			task.setTaskParam(trigger.getKey().getName());
			
			skScheduleTaskMapper.insert(task);
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void deleteJob(SkScheduleTask task) {
		
		SkScheduleTask scheduleTask = skScheduleTaskMapper.selectByPrimaryKey(task.getId());
		
		try {
			taskSheduleService.deleteCronTask(scheduleTask.getJobId(), scheduleTask.getGroupId());
			
			skScheduleTaskMapper.deleteByPrimaryKey(task.getId());
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Page<SkScheduleTask> queryScheduleTaskList(SkScheduleTask record) {
		
		Page<SkScheduleTask> task = skScheduleTaskMapper.queryForPage(record);
		
		return task;
	}

}
