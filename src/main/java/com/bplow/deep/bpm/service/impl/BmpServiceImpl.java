/**
 * www.bplow.com
 */
package com.bplow.deep.bpm.service.impl;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2016年7月9日 上午10:27:51
 */
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.transaction.Transactional;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.NoneStartEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.domain.ServiceResult;
import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmActivity;
import com.bplow.deep.bpm.domain.ProcessInstanceInfo;
import com.bplow.deep.bpm.mapper.BpmServiceMapper;
import com.bplow.deep.bpm.service.BmpService;

@Transactional
@Service
public class BmpServiceImpl implements BmpService {

    private Logger           log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RepositoryService        repositoryService;

    @Autowired
    RuntimeService           runtimeService;

    @Autowired
    TaskService              taskService;

    @Autowired
    HistoryService           historySerivce;

    @Autowired
    private IdentityService  identityService;
    
    @Autowired
    private FormService formService;

    @Autowired
    private BpmServiceMapper bpmServiceMapper;

    /**
     * 部署流程定义文件 "bpm/vacationRequest.bpmn20.xml"
     * 
     * @param xmlPath
     * @return
     */
    public String deploy(String xmlPath) {

        String deploymentId = repositoryService.createDeployment().addClasspathResource(xmlPath)
            .deploy().getId();
        return deploymentId;
    }

    /**
     * 
     * @param name
     * @param is
     * @return
     */
    public String deploy(String name, InputStream is) {
        log.info("部署流程{}", name);

        return repositoryService.createDeployment().addInputStream(name, is).deploy().getId();
    }

    public String deployByZip(String name, InputStream is) {
        ZipInputStream inputStream = new ZipInputStream(is);
        return repositoryService.createDeployment().name(name).addZipInputStream(inputStream)
            .deploy().getId();

    }

    /**
     * 启动流程实例
     * 
     * @param key 流程定义id  leave:1:97557
     * @return
     */
    public String startProcessByKey(String key, Map<String, Object> variables) {

        /*ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
            key, variables);*/
        String processName = key;
        List<ProcessDefinition> pdlist = repositoryService.createProcessDefinitionQuery().processDefinitionId(key).orderByProcessDefinitionVersion().desc().list();

        for(ProcessDefinition pd : pdlist){
            processName = pd.getName() + "wxl";
            break;
        }
        
        ProcessInstanceBuilder processBuilder = runtimeService.createProcessInstanceBuilder();
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            processBuilder.addVariable(entry.getKey(), entry.getValue());
        }

        ProcessInstance processInstance = processBuilder.processDefinitionId(key)/*processDefinitionKey(key)*/
            .processInstanceName(processName).start();

        return processInstance.getId();
    }

    public String startProcessById(String id, Map<String, Object> variables) {
        log.info("启动流程{},{}", id, variables);

        identityService.setAuthenticatedUserId("bono");

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(id, variables);

        return processInstance.getProcessInstanceId();
    }

    /**
     * 完成任务
     * 
     * @param processId
     * @param taskId
     */
    public ServiceResult completeTask(String processId, String taskId,
                                      Map<String, Object> taskVariable) {

        ServiceResult result = new ServiceResult();
        result.setResponseCode("1000");
        result.setResponseMessage("成功");

        try {
            taskService.complete(taskId, taskVariable);
        } catch (ActivitiException e) {
            log.error("完成任务[{}]异常",taskId,e);
            result.setResponseCode("999");
            result.setResponseMessage(e.getMessage());
        }

        return result;
    }

    /**
     * 接收任务
     * 
     * @param taskId
     * @param userId
     */
    public void claimTask(String taskId, String userId) {

        taskService.claim(taskId, userId);
    }

    /**
     * 转办
     */

    /**
     * 跳跃： 从一个节点跳跃到任意节点，不受线路的限制。 而且 complete 事件不能受影响。
     * 退回： 与 跳跃的差别就是在处理之前检查下任务是否处理过。
     * 驳回： 与退回一致，只是发起这不一样
     * 执行指定节点,建立在单执行线路上
     */
    protected Void execute(CommandContext commandContext, TaskEntity task) {

        String toTaskKey = null;
        Map variables = null;
        String type = null;
        if (variables != null)
            task.setExecutionVariables(variables);

        ExecutionEntity execution = task.getExecution();
        // 流程定义id
        String procDefId = execution.getProcessDefinitionId();
        // 获取服务
        RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) execution
            .getEngineServices().getRepositoryService();
        // 获取流程定义的所有节点
        ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) repositoryService
            .getDeployedProcessDefinition(procDefId);
        // 获取需要提交的节点
        ActivityImpl toActivityImpl = processDefinitionImpl.findActivity(toTaskKey);

        if (toActivityImpl == null) {
            throw new ActivitiException(toTaskKey + " to ActivityImpl is null!");
        } else {
            task.fireEvent("complete");
            Context.getCommandContext().getTaskEntityManager().deleteTask(task, type, false);
            execution.removeTask(task);// 执行规划的线
            execution.executeActivity(toActivityImpl);
        }
        return null;
    }

    /** 
     * 会签操作 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param userCodes 
     *            会签人账号集合 
     * @throws Exception 
     */
    public void jointProcess(String taskId, List<String> userCodes) throws Exception {
        for (String userCode : userCodes) {
            TaskEntity task = (TaskEntity) taskService.newTask(""/*IDGenerator.generateID()*/);
            task.setAssignee(userCode);
            task.setName("" + "-会签");
            task.setProcessDefinitionId(""/*findProcessDefinitionEntityByTaskId(  
                                          taskId).getId()*/);
            task.setProcessInstanceId(""/*findProcessInstanceByTaskId(taskId).getId()*/);
            task.setParentTaskId(taskId);
            task.setDescription("jointProcess");
            taskService.saveTask(task);
        }
    }

    /**
     * 暂停流程
     * 
     * @param key
     */
    public void suspendProcess(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    /**
     * 删除流程
     */
    public void deleletProcessById(String processInstanceId, String deleteReason) {

        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }

    /**
     * 获取部署的图片
     */
    public InputStream getImageInputStream(String processDefinitionId) {

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
            .processDefinitionId(processDefinitionId).singleResult();

        String diagramResourceName = processDefinition.getDiagramResourceName();

        InputStream imageStream = repositoryService.getResourceAsStream(
            processDefinition.getDeploymentId(), diagramResourceName);

        return imageStream;
    }

    public InputStream getImageInputStreamById(ProcessInstanceInfo processInfo) {
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processInfo
            .getKey());
        //        .processDefinitionKey(processDefinitionKey).listPage(0, 1).get(0);
        ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

        if (bpmnModel.getLocationMap().size() == 0) {
            BpmnAutoLayout autoLayout = new BpmnAutoLayout(bpmnModel);
            autoLayout.execute();
        }

        InputStream is = processDiagramGenerator.generateDiagram(bpmnModel,"jpg","宋体","微软雅黑",null);
        return is;
    }

    /**
     * 获取流程实例列表
     */
    public Page<ProcessInstanceInfo> queryProcessInstanceItem(ProcessInstanceInfo processInfo) {

        /*Long count = historySerivce.createHistoricProcessInstanceQuery().count();

        List list = historySerivce.createHistoricProcessInstanceQuery().listPage(firstResult,
            maxResults);*/

        Page<ProcessInstanceInfo> processItem = bpmServiceMapper
            .queryProcessInstanceItemForPage(processInfo);

        //runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId);

        return processItem;
    }

    /**
     * 获取单条流程实例
     */
    public ProcessInstanceInfo queryProcessInstance(ProcessInstanceInfo processInfo) {
        ProcessInstanceInfo processInstance = new ProcessInstanceInfo();
        ProcessInstance obj = runtimeService.createProcessInstanceQuery()
            .processInstanceId(processInfo.getProcessInstanceId()).singleResult();

        return processInstance;
    }

    /**
     * 获取实例任务
     */
    public List<Task> getTaskListByGroup(String group) {

        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(group).list();

        return tasks;

    }

    /**
     * 获取历史任务列表
     */
    public List<ProcessInstanceInfo> getHistoryProcess(String processInstanceId){
        
        List<HistoricTaskInstance> list = historySerivce.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskCreateTime().asc().list();
        List<ProcessInstanceInfo> processInsInfoList = new ArrayList<ProcessInstanceInfo>();
        for(HistoricTaskInstance historicTaskIns : list){
            ProcessInstanceInfo processIns = new ProcessInstanceInfo();
            
            processIns.setProcessInstanceId(historicTaskIns.getProcessInstanceId());
            processIns.setAssignee(historicTaskIns.getAssignee());
            processIns.setActiviteName(historicTaskIns.getName());
            processIns.setStartDate(historicTaskIns.getStartTime());
            processIns.setEndDate(historicTaskIns.getEndTime());
            processIns.setProcessStatus(historicTaskIns.getDeleteReason());
            processIns.setTaskId(historicTaskIns.getId());
            
            processInsInfoList.add(processIns);
        }
        
        return processInsInfoList;
    }
    

    /**
     * 获取流程定义
     */
    public List<ProcessDefinition> getProcesDef(String def) {

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();

        return list;
    }

    @Override
    public ProcessInstanceInfo queryTask(ProcessInstanceInfo processInfo) {
        ProcessInstanceInfo process = new ProcessInstanceInfo();
        Task task = taskService.createTaskQuery().taskId(processInfo.getTaskId()).singleResult();

        process.setTaskId(task.getId());
        process.setActiviteName(task.getName());
        process.setProcessInstanceId(task.getProcessInstanceId());
        process.setKey(task.getTaskDefinitionKey());
        process.setProcessDefineId(task.getProcessDefinitionId());

        return process;
    }

    /**
     * 任务列表
     */
    @Override
    public Page<ProcessInstanceInfo> queryTaskItem(ProcessInstanceInfo processInfo) {

        Page<ProcessInstanceInfo> task = bpmServiceMapper.queryTasksForPage(processInfo);

        return task;
    }

	@Override
	public String queryProcessStartForm(ProcessInstanceInfo processInfo) {
		
		ProcessDefinition pd= repositoryService.createProcessDefinitionQuery().processDefinitionKey(processInfo.getKey()).latestVersion().singleResult();
		
		String formKey = formService.getStartFormData(pd.getId()).getFormKey();
		
		return formKey;
	}
	
	public List<BpmActivity> queryAllActivity(String processDefinitionId){
	    
	    String procDefId = processDefinitionId;//"leave:1:97557"
        ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) repositoryService
                .getProcessDefinition(procDefId);
        
        List<ActivityImpl> activities = processDefinitionImpl.getActivities();
        List<BpmActivity> userTaskActs = new ArrayList<BpmActivity>();
        
        for(ActivityImpl act : activities){
           if(act.getActivityBehavior() instanceof UserTaskActivityBehavior || act.getActivityBehavior() instanceof NoneStartEventActivityBehavior ){
               BpmActivity bpmAct = new BpmActivity();
               
               bpmAct.setId(act.getId());
               bpmAct.setName(act.getId());
               
               userTaskActs.add(bpmAct);
           }
        }
	    
        return userTaskActs;
	}

    @Override
    public Map queryFormValuesByProcessId(String processInstanceId) {
        Map<String,Object> map = new HashMap<String,Object>();
        List<HistoricVariableInstance> list = historySerivce.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        
        for(HistoricVariableInstance var : list){
          map.put(var.getVariableName(),var.getValue());
        }
        
        return map;
    }

}
