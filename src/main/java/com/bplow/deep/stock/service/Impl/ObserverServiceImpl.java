/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import ognl.Ognl;
import ognl.OgnlContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.stock.service.ObserverService;
import com.bplow.deep.stock.service.QueryStockPriceRealTimeSerivce;
import com.bplow.deep.stock.service.SendMessageService;
import com.bplow.deep.stock.vo.StockInfo;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月23日 下午9:48:22
 */
@Service
public class ObserverServiceImpl implements ObserverService{
    
    private Logger           logger = LoggerFactory.getLogger(this.getClass());
    
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
		OgnlContext context = (OgnlContext) Ognl.createDefaultContext(null);
		Object root = null;
		context.put("a", 1);
        context.put("b", 2);
		String expression = "a > b";
		try {
            Object node = Ognl.compileExpression(context, root, expression);
            //node =Ognl.parseExpression(expression);
            
            Object result = Ognl.getValue( node, context);
            
            
            System.out.println(result);
        } catch (Exception e) {
            logger.error("", e);
        }
		//发送短信
		
		
	}

}
