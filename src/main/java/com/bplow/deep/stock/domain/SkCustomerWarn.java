package com.bplow.deep.stock.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.bplow.deep.base.jackson.CustomDateSerializer;
import com.bplow.deep.base.pagination.PageInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class SkCustomerWarn extends PageInfo{
    
    /**  */
    private static final long serialVersionUID = 7370618433717795490L;

    private String id;

    private String userId;

    private String ruleId;

    private String stockId;
    
    private Double value;

    private String status;

    private Date gmtCreate;
    
    private Date gmtModify;
    
    //扩展参数
    
    private boolean wave;
    
    private boolean high;
    
    private boolean lower;
    
    private BigDecimal   waveValue;
    
    private BigDecimal   highValue;
    
    private BigDecimal   lowerValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId == null ? null : stockId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isWave() {
        return wave;
    }

    public void setWave(boolean wave) {
        this.wave = wave;
    }

    public boolean isHigh() {
        return high;
    }

    public void setHigh(boolean high) {
        this.high = high;
    }

    public boolean isLower() {
        return lower;
    }

    public void setLower(boolean lower) {
        this.lower = lower;
    }

    public BigDecimal getWaveValue() {
        return waveValue;
    }

    public void setWaveValue(BigDecimal waveValue) {
        this.waveValue = waveValue;
    }

    public BigDecimal getHighValue() {
        return highValue;
    }

    public void setHighValue(BigDecimal highValue) {
        this.highValue = highValue;
    }

    public BigDecimal getLowerValue() {
        return lowerValue;
    }

    public void setLowerValue(BigDecimal lowerValue) {
        this.lowerValue = lowerValue;
    }
    
}