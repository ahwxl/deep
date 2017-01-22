package com.bplow.deep.bpm.domain;

import java.io.Serializable;
import java.util.Date;

import com.bplow.deep.base.pagination.PageInfo;

/**
 * 流程实例信息
 * 
 * @author wangxiaolei
 * @version $Id: ProcessInstanceInfo.java, v 0.1 2017年1月22日 下午3:23:41 wangxiaolei Exp $
 */
public class ProcessInstanceInfo extends PageInfo implements Serializable{

    /**  */
    private static final long serialVersionUID = -2937509678062965968L;
    
    private String processInstanceId;
    private String taskId;
    private String processName;
    private String activiteName;
    private String processStatus;
    private String currentActive;
    private String currentProcessUserId;
    private Date   startDate;
    private Date   endDate;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getCurrentActive() {
        return currentActive;
    }

    public void setCurrentActive(String currentActive) {
        this.currentActive = currentActive;
    }

    public String getCurrentProcessUserId() {
        return currentProcessUserId;
    }

    public void setCurrentProcessUserId(String currentProcessUserId) {
        this.currentProcessUserId = currentProcessUserId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getActiviteName() {
        return activiteName;
    }

    public void setActiviteName(String activiteName) {
        this.activiteName = activiteName;
    }

}
