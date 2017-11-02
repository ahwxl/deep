/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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
public class QueryStockPriceRealTimeServiceImpl implements QueryStockPriceRealTimeSerivce,
                                               InitializingBean {

    private Logger                 log          = LoggerFactory.getLogger(this.getClass());

    private Map<String, StockInfo> stockInfoMap = new ConcurrentHashMap<String, StockInfo>();

    private String                 url          = "http://hq.sinajs.cn/list=";

    private HttpClient             client;

    @Autowired
    private StockWareHouseService  stockWareHouseService;

    @Override
    public void afterPropertiesSet() throws Exception {

        client = new HttpClient();

    }

    /**
     * 单个股票查询
     */
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

    /**
     * 查询所有用户持仓的股票，事实价格
     * 
     * 当用户数过大时，这里就会有缺陷
     * 
    var hq_str_s_sh000100="上证F500,5225.5397,-28.8185,-0.55,769252,8794516";
    var hq_str_s_sh600822="上海物贸,15.660,-0.770,-4.69,278588,43998";
    var hq_str_s_sh601212="白银有色,8.560,0.000,0.00,0,0";
    var hq_str_s_sz000600="建投能源,9.62,-0.16,-1.64,94113,9021";
    var hq_str_s_sh600078="澄星股份,5.710,-0.040,-0.70,14969,857";
     */
    public String queryPrice(String stocks) {
        //这里需要改进
        SkWarehousePositon position = new SkWarehousePositon();
        position.setPageSize(10000);
        Page<SkWarehousePositon> page = stockWareHouseService.queryWarehouse(position);

        List<SkWarehousePositon> list = page.getDatas();

        StringBuilder sb = new StringBuilder();
        Set<String> stockIdSet = new HashSet<String>();

        for (SkWarehousePositon postion : list) {
            stockIdSet.add(postion.getStockId().trim());
        }

        for (String tmpStockId : stockIdSet) {
            sb.append("s_").append(tmpStockId).append(",");
        }

        if (sb.length() == 0)
            return null;

        String requestUrl = url + sb.substring(0, sb.length() - 1);

        String responseMsg = request(requestUrl);

        String[] stockArrayStr = responseMsg.split("\n");

        for (String stockStr : stockArrayStr) {

            Pattern p = Pattern.compile("\"(.*?)\"");
            Matcher m = p.matcher(stockStr);
            ArrayList<String> strs = new ArrayList<String>();

            while (m.find()) {
                strs.add(m.group(1));
            }
            if (strs.size() > 0) {

                for (String aimStr : strs) {
                    String[] fieldArray = aimStr.split(",");
                    StockInfo stockInfoTmp = stockInfoMap.get(fieldArray[0]);
                    if (stockInfoTmp == null) {
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
