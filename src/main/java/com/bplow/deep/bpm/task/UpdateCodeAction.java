package com.bplow.deep.bpm.task;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class UpdateCodeAction implements JavaDelegate{

    @Override
    public void execute(DelegateExecution execution) throws Exception {
    
        RuntimeService runtimeService = execution.getEngineServices().getRuntimeService();
    
        //执行异常暂停流程
        
        String processInstanceId = execution.getProcessInstanceId();
        
        runtimeService.suspendProcessInstanceById(processInstanceId);
        
    }

}
