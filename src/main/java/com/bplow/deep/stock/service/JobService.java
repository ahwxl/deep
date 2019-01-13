/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service;

import org.springframework.transaction.annotation.Transactional;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkScheduleTask;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月26日 下午9:28:57
 */
@Transactional
public interface JobService {
	
	public void createJob(SkScheduleTask task);
	
	public void editerJob(SkScheduleTask task);
	
	public void deleteJob(SkScheduleTask task);
	
	public boolean triggerJob(SkScheduleTask task);
	
	public boolean pauseJob(SkScheduleTask task);
	
	public boolean resumeJob(SkScheduleTask task);
	
	public Page<SkScheduleTask> queryScheduleTaskList(SkScheduleTask task);
	
	public SkScheduleTask queryJob(SkScheduleTask task);
	

}
