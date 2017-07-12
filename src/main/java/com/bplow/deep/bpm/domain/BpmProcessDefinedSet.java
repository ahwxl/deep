package com.bplow.deep.bpm.domain;

import com.bplow.deep.base.pagination.PageInfo;

public class BpmProcessDefinedSet extends PageInfo{

    /**  */
    private static final long serialVersionUID = 2856060831066990414L;

    private String processDefinedId;

    private String activityId;

    private Integer formId;

    private String userId;

    private String roleId;

    public String getProcessDefinedId() {
        return processDefinedId;
    }

    public void setProcessDefinedId(String processDefinedId) {
        this.processDefinedId = processDefinedId == null ? null : processDefinedId.trim();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

}