package com.bplow.deep.bpm.domain;

import java.util.Date;

import com.bplow.deep.base.pagination.PageInfo;

public class BpmProcessDefined extends PageInfo{

	private static final long serialVersionUID = 8416555818900458619L;

	private Long id;

    private String processDefinedId;

    private Integer formId;
    
    private String key;
    
    private String name;

    private Date gmtCreate;

    private Date gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

	public String getProcessDefinedId() {
		return processDefinedId;
	}

	public void setProcessDefinedId(String processDefinedId) {
		this.processDefinedId = processDefinedId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}