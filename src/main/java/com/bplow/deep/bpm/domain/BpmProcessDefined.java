package com.bplow.deep.bpm.domain;

import java.util.Date;

import com.bplow.deep.base.pagination.PageInfo;

public class BpmProcessDefined extends PageInfo{

    private Long id;

    private Long processDefinedId;

    private Long formId;

    private Date gmtCreate;

    private Date gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcessDefinedId() {
        return processDefinedId;
    }

    public void setProcessDefinedId(Long processDefinedId) {
        this.processDefinedId = processDefinedId;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

}