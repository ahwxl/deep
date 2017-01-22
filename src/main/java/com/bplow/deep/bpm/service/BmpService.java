/**
 * www.bplow.com
 */
package com.bplow.deep.bpm.service;

import java.util.List;
import java.util.Map;

import com.bplow.deep.base.domain.ServiceResult;
import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.ProcessInstanceInfo;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2016年7月12日 下午9:14:45
 */
public interface BmpService {
	
	//部署
	public String deploy(String xmlPath);
	//创建流程实例
	public String startProcessByKey(String key, Map<String, Object> variables);
	//完成任务
	public ServiceResult completeTask(String processId, String taskId, Map<String, Object> taskVariable);
	//接受任务
	public void claimTask(String taskId, String userId);
	
	//流程列表
	public Page<ProcessInstanceInfo> queryProcessInstanceItem(ProcessInstanceInfo processInfo,int firstResult, int maxResults);
	//流程实例
	public ProcessInstanceInfo queryProcessInstance(ProcessInstanceInfo processInfo);
	
	public List<ProcessInstanceInfo> queryTaskItem(ProcessInstanceInfo processInfo);
	
	public ProcessInstanceInfo queryTask(ProcessInstanceInfo processInfo);
}
