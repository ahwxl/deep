/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkWarehousePositon;
import com.bplow.deep.stock.service.QueryStockPriceRealTimeSerivce;
import com.bplow.deep.stock.service.StockWareHouseService;
import com.bplow.deep.stock.vo.StockInfo;

/**
 * @desc
 * @author wangxiaolei
 * @date 2017年2月23日 下午9:45:01
 */
@Service("QueryStockPriceRealTimeSerivce")
public class QueryStockPriceRealTimeServiceImpl implements
		QueryStockPriceRealTimeSerivce,InitializingBean {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private Map<String,StockInfo> stockInfoMap = new HashMap<String,StockInfo>();

	private String url = "http://hq.sinajs.cn/list=";
	
	private HttpClient client;
	
	@Autowired
	private StockWareHouseService stockWareHouseService;
	
	@Override
    public void afterPropertiesSet() throws Exception {
	    
	    client = new HttpClient();
	    
    }

	@Override
	public StockInfo queryPrice(StockInfo stockInfo) {
		StockInfo stock = new StockInfo();

		String requestUrl = url + "s_" + stockInfo.getStockId();

		String responseMsg = request(requestUrl);
		
		Pattern p = Pattern.compile("\"(.*?)\"");
		Matcher m = p.matcher(responseMsg);
		ArrayList<String> strs = new ArrayList<String>();
		
		while (m.find()) {
			strs.add(m.group(1));
		}
		if (strs.size() > 0) {
			String aimStr = strs.get(0);
			String[] fieldArray = aimStr.split(",");
			if (fieldArray.length > 5) {
			    stock.setStockName(fieldArray[0]);
				stock.setCurrentPrice(new BigDecimal(fieldArray[1]));
				stock.setWave(new BigDecimal(fieldArray[3]));
			}
		}

		return stock;
	}

	public String queryPrice(String stocks){
		SkWarehousePositon position = new SkWarehousePositon();
		position.setPageSize(10000);
		Page<SkWarehousePositon> page = stockWareHouseService.queryWarehouse(position);
		
		List<SkWarehousePositon> list = page.getDatas();
		
		StringBuilder sb = new StringBuilder();
		for(SkWarehousePositon postion : list ){
			sb.append("s_").append(postion.getStockId()).append(",");
		}
		
		if(sb.length() == 0 ) return null;
		
		String requestUrl =  url+sb.substring(0,sb.length()-1);
		
		String responseMsg = request(requestUrl);
		
		String[] stockArrayStr = responseMsg.split("\n");
		
		for(String stockStr : stockArrayStr){
			
			Pattern p = Pattern.compile("\"(.*?)\"");
			Matcher m = p.matcher(stockStr);
			ArrayList<String> strs = new ArrayList<String>();
			
			while (m.find()) {
				strs.add(m.group(1));
			}
			if (strs.size() > 0) {
				
				for(String aimStr : strs){
					String[] fieldArray = aimStr.split(",");
					StockInfo stockInfoTmp = stockInfoMap.get(fieldArray[0]);
					if(stockInfoTmp == null){
						stockInfoTmp = new StockInfo();
						stockInfoMap.put(fieldArray[0], stockInfoTmp);
					}
					
					if (fieldArray.length > 5) {
						stockInfoTmp.setStockName(fieldArray[0]);
						stockInfoTmp.setCurrentPrice(new BigDecimal(fieldArray[1]));
						stockInfoTmp.setWave(new BigDecimal(fieldArray[3]));
					}
				}
				
				
			}
			
		}
		
		return null;
	}

	public String request(String url) {
		String responseMsg = null;
		GetMethod method = new GetMethod(url);

		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		try {
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			byte[] responseBody = method.getResponseBody();
			responseMsg = new String(responseBody, "GBK");

			log.info("{}", responseMsg);

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}

		return responseMsg;
	}
	
	public Map<String, StockInfo> getStockInfoMap() {
		return stockInfoMap;
	}

	public static void main(String[] args) {

		QueryStockPriceRealTimeServiceImpl obj = new QueryStockPriceRealTimeServiceImpl();
		/*StockInfo stockInfo = new StockInfo();
		stockInfo.setStockId("sh600078");
		stockInfo = obj.queryPrice(stockInfo);

		System.out.println(stockInfo.getWave().abs().doubleValue());*/
		
		obj.queryPrice("s_sh600078,s_sz000600");
		
	}

}
