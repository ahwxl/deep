/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service;

import java.util.Map;

import com.bplow.deep.stock.vo.StockInfo;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月23日 下午9:38:15
 */
public interface QueryStockPriceRealTimeSerivce {
	
	
	public StockInfo queryPrice(StockInfo stockInfo);
	
	public String queryPrice(String url);
	
	public Map<String, StockInfo> getStockInfoMap();

}
