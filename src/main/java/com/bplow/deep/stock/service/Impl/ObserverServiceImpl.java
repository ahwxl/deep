/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.bplow.deep.stock.service.ObserverService;
import com.bplow.deep.stock.service.QueryStockPriceRealTimeSerivce;
import com.bplow.deep.stock.service.SendMessageService;
import com.bplow.deep.stock.vo.StockInfo;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月23日 下午9:48:22
 */
public class ObserverServiceImpl implements ObserverService{

	@Autowired
	private SendMessageService sendMessageService;
	
	@Autowired
	private QueryStockPriceRealTimeSerivce queryStockPriceRealTimeSerivce;
	
	
	@Override
	public void observer(String taskId) {
		
		StockInfo stockInfo = new StockInfo();
		stockInfo.setStockId("sh600078");
		
		StockInfo aimStock = queryStockPriceRealTimeSerivce.queryPrice(stockInfo);
		
		//获取股票昨日信息
		
		//比较当前价格
		
		//获取所有规则
		
		//检查规则是否命中
		
		//发送短信
		
		
	}

}
