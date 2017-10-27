package com.bplow.deep.stock.domain;

import java.util.Date;

import com.bplow.deep.base.jackson.CustomDateSerializer;
import com.bplow.deep.base.pagination.PageInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class SkWarehousePositon extends PageInfo{
	
    /**  */
    private static final long serialVersionUID = 4058004332632703728L;

    private String userId;
    
    private String stockId;

    private String stockName;

    private Long amount;

    private Double yesterPrice;

    private Double todayPrice;

    private Double marketValue;
    
    private Double exceptPrice;
    
    private Long   exceptAmount;

    private Long sortBy;

    private Date gmtCreate;

    private Date gmtModify;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId == null ? null : stockId.trim();
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName == null ? null : stockName.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getYesterPrice() {
        return yesterPrice;
    }

    public void setYesterPrice(Double yesterPrice) {
        this.yesterPrice = yesterPrice;
    }

    public Double getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(Double todayPrice) {
        this.todayPrice = todayPrice;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public Long getSortBy() {
        return sortBy;
    }

    public void setSortBy(Long sortBy) {
        this.sortBy = sortBy;
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
    
    public Double getExceptPrice() {
        return exceptPrice;
    }

    public void setExceptPrice(Double exceptPrice) {
        this.exceptPrice = exceptPrice;
    }

    public Long getExceptAmount() {
        return exceptAmount;
    }

    public void setExceptAmount(Long exceptAmount) {
        this.exceptAmount = exceptAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}