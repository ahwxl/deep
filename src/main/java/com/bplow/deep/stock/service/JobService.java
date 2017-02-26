/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkScheduleTask;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月26日 下午9:28:57
 */
public interface JobService {
	
	public void createJob(SkScheduleTask task);
	
	public void deleteJob(SkScheduleTask task);
	
	public Page<SkScheduleTask> queryScheduleTaskList(SkScheduleTask task);
	

}
