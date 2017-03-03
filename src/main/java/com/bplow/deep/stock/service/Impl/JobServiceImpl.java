/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkScheduleTask;
import com.bplow.deep.stock.mapper.SkScheduleTaskMapper;
import com.bplow.deep.stock.service.JobService;

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

	@Override
	public void createJob(SkScheduleTask task) {
		
		task.setId(UUID.randomUUID().toString().replace("-", ""));
		skScheduleTaskMapper.insert(task);
		
	}

	@Override
	public void deleteJob(SkScheduleTask task) {
		skScheduleTaskMapper.deleteByPrimaryKey(task.getId());
	}

	@Override
	public Page<SkScheduleTask> queryScheduleTaskList(SkScheduleTask record) {
		
		Page<SkScheduleTask> task = skScheduleTaskMapper.queryForPage(record);
		
		return task;
	}

}
