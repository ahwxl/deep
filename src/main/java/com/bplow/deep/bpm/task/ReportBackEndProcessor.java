package com.bplow.deep.bpm.task;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销假后处理器
 * <p>设置销假时间</p>
 * <p>使用Spring代理，可以注入Bean，管理事物</p>
 *
 * @author HenryYan
 */
@Component
@Transactional
public class ReportBackEndProcessor implements TaskListener {
    
    private Logger           log = LoggerFactory.getLogger(this.getClass());

    private static final long serialVersionUID = 1L;

    @Autowired
    RuntimeService runtimeService;

    /* (non-Javadoc)
     * @see org.activiti.engine.delegate.TaskListener#notify(org.activiti.engine.delegate.DelegateTask)
     */
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        Object realityStartTime = delegateTask.getVariable("deptLeaderPass");

        log.info("销假{}",realityStartTime);
    }

}
