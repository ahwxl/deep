package com.bplow.deep.stock.domain;

import java.util.Date;

import com.bplow.deep.base.pagination.PageInfo;

public class SkWarehousePositon extends PageInfo{
    private String stockId;

    private String stockName;

    private Long amount;

    private Double yesterPrice;

    private Double todayPrice;

    private Double marketValue;

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
     * This method corresponds to the database table sk_warehouse_positon
     *
     * @mbggenerated Thu Feb 23 16:31:39 CST 2017
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
        SkWarehousePositon other = (SkWarehousePositon) that;
        return (this.getStockId() == null ? other.getStockId() == null : this.getStockId().equals(other.getStockId()))
            && (this.getStockName() == null ? other.getStockName() == null : this.getStockName().equals(other.getStockName()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getYesterPrice() == null ? other.getYesterPrice() == null : this.getYesterPrice().equals(other.getYesterPrice()))
            && (this.getTodayPrice() == null ? other.getTodayPrice() == null : this.getTodayPrice().equals(other.getTodayPrice()))
            && (this.getMarketValue() == null ? other.getMarketValue() == null : this.getMarketValue().equals(other.getMarketValue()))
            && (this.getSortBy() == null ? other.getSortBy() == null : this.getSortBy().equals(other.getSortBy()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModify() == null ? other.getGmtModify() == null : this.getGmtModify().equals(other.getGmtModify()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sk_warehouse_positon
     *
     * @mbggenerated Thu Feb 23 16:31:39 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStockId() == null) ? 0 : getStockId().hashCode());
        result = prime * result + ((getStockName() == null) ? 0 : getStockName().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getYesterPrice() == null) ? 0 : getYesterPrice().hashCode());
        result = prime * result + ((getTodayPrice() == null) ? 0 : getTodayPrice().hashCode());
        result = prime * result + ((getMarketValue() == null) ? 0 : getMarketValue().hashCode());
        result = prime * result + ((getSortBy() == null) ? 0 : getSortBy().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModify() == null) ? 0 : getGmtModify().hashCode());
        return result;
    }
}