/**
 * www.bplow.com
 */
package com.bplow.deep.stock.vo;

import java.math.BigDecimal;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月23日 下午9:43:44
 */
public class StockInfo {
	
	private String stockId;
	private String stockName;
	private BigDecimal yesterPrice;
	private BigDecimal currentPrice;
	private BigDecimal wave;
	
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public BigDecimal getYesterPrice() {
		return yesterPrice;
	}
	public void setYesterPrice(BigDecimal yesterPrice) {
		this.yesterPrice = yesterPrice;
	}
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	public BigDecimal getWave() {
		return wave;
	}
	public void setWave(BigDecimal wave) {
		this.wave = wave;
	}
	@Override
	public String toString() {
		return String
				.format("StockInfo [stockId=%s, stockName=%s, yesterPrice=%s, currentPrice=%s, wave=%s]",
						stockId, stockName, yesterPrice, currentPrice, wave);
	}
	
}
