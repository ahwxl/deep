package com.bplow.deep.bpm.domain;

import java.util.Date;

import com.bplow.deep.base.jackson.CustomDateSerializer;
import com.bplow.deep.base.pagination.PageInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class BpmForm extends PageInfo {

    /**  */
    private static final long serialVersionUID = -2582556813168767960L;

    private Integer              formId;

    private String            formName;

    private String            formDesc;

    private String            formContent;
    
    private Integer           parentFormId;

    private Date              gmtCreate;

    private Date              gmtModify;

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName == null ? null : formName.trim();
    }

    public String getFormDesc() {
        return formDesc;
    }

    public void setFormDesc(String formDesc) {
        this.formDesc = formDesc == null ? null : formDesc.trim();
    }

    public String getFormContent() {
        return formContent;
    }

    public void setFormContent(String formContent) {
        this.formContent = formContent == null ? null : formContent.trim();
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Integer getParentFormId() {
        return parentFormId;
    }

    public void setParentFormId(Integer parentFormId) {
        this.parentFormId = parentFormId;
    }

    
}