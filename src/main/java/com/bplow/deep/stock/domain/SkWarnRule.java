package com.bplow.deep.stock.domain;

import java.util.Date;

import com.bplow.deep.base.pagination.PageInfo;

public class SkWarnRule extends PageInfo{
    private String ruleId;

    private String scripte;

    private String ruleMsg;

    private String status;

    private Date gmtCreate;

    private Date gmtModify;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getScripte() {
        return scripte;
    }

    public void setScripte(String scripte) {
        this.scripte = scripte == null ? null : scripte.trim();
    }

    public String getRuleMsg() {
        return ruleMsg;
    }

    public void setRuleMsg(String ruleMsg) {
        this.ruleMsg = ruleMsg == null ? null : ruleMsg.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sk_warn_rule
     *
     * @mbggenerated Sat Feb 25 16:50:55 CST 2017
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SkWarnRule other = (SkWarnRule) that;
        return (this.getRuleId() == null ? other.getRuleId() == null : this.getRuleId().equals(other.getRuleId()))
            && (this.getScripte() == null ? other.getScripte() == null : this.getScripte().equals(other.getScripte()))
            && (this.getRuleMsg() == null ? other.getRuleMsg() == null : this.getRuleMsg().equals(other.getRuleMsg()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModify() == null ? other.getGmtModify() == null : this.getGmtModify().equals(other.getGmtModify()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sk_warn_rule
     *
     * @mbggenerated Sat Feb 25 16:50:55 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRuleId() == null) ? 0 : getRuleId().hashCode());
        result = prime * result + ((getScripte() == null) ? 0 : getScripte().hashCode());
        result = prime * result + ((getRuleMsg() == null) ? 0 : getRuleMsg().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModify() == null) ? 0 : getGmtModify().hashCode());
        return result;
    }
}