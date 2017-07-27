package com.bplow.deep.bpm.domain;

import java.io.Serializable;
import java.util.Date;

import com.bplow.deep.base.jackson.CustomDateSerializer;
import com.bplow.deep.base.pagination.PageInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 流程实例信息
 * 
 * @author wangxiaolei
 * @version $Id: ProcessInstanceInfo.java, v 0.1 2017年1月22日 下午3:23:41 wangxiaolei Exp $
 */
public class ProcessInstanceInfo extends PageInfo implements Serializable{

    /**  */
    private static final long serialVersionUID = -2937509678062965968L;
    
    private String key;
    private String processInstanceId;
    private String processDefineId;
    private String taskId;
    private String processName;
    private String activiteName;
    private String processStatus;
    private String currentActive;
    private String assignee;//受理人
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

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

	public String getProcessDefineId() {
		return processDefineId;
	}

	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

}
