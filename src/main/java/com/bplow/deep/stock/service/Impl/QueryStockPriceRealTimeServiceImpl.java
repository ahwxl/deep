/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.stereotype.Service;

import com.bplow.deep.stock.service.QueryStockPriceRealTimeSerivce;
import com.bplow.deep.stock.vo.StockInfo;

/**
 * @desc
 * @author wangxiaolei
 * @date 2017年2月23日 下午9:45:01
 */
@Service
public class QueryStockPriceRealTimeServiceImpl implements
		QueryStockPriceRealTimeSerivce {
	
	private Logger           log = LoggerFactory.getLogger(this.getClass());

	private String url = "http://hq.sinajs.cn/list=";

	@Override
	public StockInfo queryPrice(StockInfo stockInfo) {
		StockInfo stock = new StockInfo();
		
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url+"s_"+stockInfo.getStockId());
		
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
	    		new DefaultHttpMethodRetryHandler(3, false));

		try {
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();
			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			String responseMsg = new String(responseBody,"GBK");
			log.info("{}",responseMsg);
			
			Pattern p = Pattern.compile("\"(.*?)\"");
	        Matcher m = p.matcher(responseMsg);
	        ArrayList<String> strs = new ArrayList<String>();
	        while (m.find()) {
	            strs.add(m.group(1));
	        } 
	        if(strs.size()>0){
	        	String aimStr = strs.get(0);
	        	String[] fieldArray = aimStr.split(",");
	        	if(fieldArray.length > 5){
	        		stock.setCurrentPrice(new BigDecimal(fieldArray[1]));
	        		stock.setWave(new BigDecimal(fieldArray[3]));
	        	}
	        }

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

		return stock;
	}
	
	public static void main(String[] args) {
		
		QueryStockPriceRealTimeServiceImpl obj = new QueryStockPriceRealTimeServiceImpl();
		StockInfo stockInfo = new StockInfo();
		stockInfo.setStockId("sh600078");
		stockInfo = obj.queryPrice(stockInfo);
		
		System.out.println(stockInfo.getWave().abs().doubleValue());
	}

}
