/**
 * www.bplow.com
 */
package com.bplow.deep.bpm.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.process.ActivityImpl;

import com.bplow.deep.base.domain.ServiceResult;
import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmActivity;
import com.bplow.deep.bpm.domain.ProcessInstanceInfo;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2016年7月12日 下午9:14:45
 */
public interface BmpService {
	
	//部署
	public String deploy(String xmlPath);
	public String deploy(String name, InputStream is);
	//创建流程实例
	public String startProcessByKey(String key, Map<String, Object> variables);
	//完成任务
	public ServiceResult completeTask(String processId, String taskId, Map<String, Object> taskVariable);
	//接受任务
	public void claimTask(String taskId, String userId);
	
	//查询流程创建表单
	public String queryProcessStartForm(ProcessInstanceInfo processInfo);
	//流程列表
	public Page<ProcessInstanceInfo> queryProcessInstanceItem(ProcessInstanceInfo processInfo);
	//流程实例
	public ProcessInstanceInfo queryProcessInstance(ProcessInstanceInfo processInfo);
	
	public Page<ProcessInstanceInfo> queryTaskItem(ProcessInstanceInfo processInfo);
	
	public ProcessInstanceInfo queryTask(ProcessInstanceInfo processInfo);
	//流程图
	public InputStream getImageInputStreamById(ProcessInstanceInfo processInfo);
	//查询所有用户活动列表
	public List<BpmActivity> queryAllActivity(String processDefinitionId);
}
