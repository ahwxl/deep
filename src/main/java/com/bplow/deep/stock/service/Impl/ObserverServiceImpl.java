/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.base.utils.DateUtils;
import com.bplow.deep.base.velocity.VelocityUtils;
import com.bplow.deep.stock.domain.SkCustomerWarn;
import com.bplow.deep.stock.domain.SkSendSmsLog;
import com.bplow.deep.stock.domain.SkWarehousePositon;
import com.bplow.deep.stock.domain.SkWarnLog;
import com.bplow.deep.stock.domain.SkWarnRule;
import com.bplow.deep.stock.mapper.SkCustomerWarnMapper;
import com.bplow.deep.stock.mapper.SkSendSmsLogMapper;
import com.bplow.deep.stock.mapper.SkWarehousePositonMapper;
import com.bplow.deep.stock.mapper.SkWarnLogMapper;
import com.bplow.deep.stock.mapper.SkWarnRuleMapper;
import com.bplow.deep.stock.service.ObserverService;
import com.bplow.deep.stock.service.QueryStockPriceRealTimeSerivce;
import com.bplow.deep.stock.service.ScriptService;
import com.bplow.deep.stock.service.SendMessageService;
import com.bplow.deep.stock.service.SystemUserService;
import com.bplow.deep.stock.vo.Message;
import com.bplow.deep.stock.vo.StockInfo;
import com.bplow.deep.sysmng.domain.SysUser;

/**
 * @desc   一个任务只能 对于一个用户 和 一个股票
 * @author wangxiaolei
 * @date 2017年2月23日 下午9:48:22
 */
@Service("observerService")
public class ObserverServiceImpl implements ObserverService, InitializingBean {

    private Logger                         logger          = LoggerFactory.getLogger(this
                                                               .getClass());

    Map<String, List<SkCustomerWarn>>      customerWarnMap = new ConcurrentHashMap<String, List<SkCustomerWarn>>();

    Map<String, SkWarnRule>                rulesMap        = new ConcurrentHashMap<String, SkWarnRule>();

    @Autowired
    private ScriptService                  scriptService;

    @Autowired
    private SendMessageService             sendMessageService;

    @Autowired
    private QueryStockPriceRealTimeSerivce queryStockPriceRealTimeSerivce;

    @Autowired
    private SystemUserService              systemUserService;

    @Autowired
    private SkWarnRuleMapper               skWarnRuleMapper;

    @Autowired
    private SkWarnLogMapper                skWarnLogMapper;

    @Autowired
    private SkCustomerWarnMapper           skCustomerWarnMapper;

    @Autowired
    private SkSendSmsLogMapper             skSendSmsLogMapper;

    @Autowired
    SkWarehousePositonMapper               skWarehousePositonMapper;

    @Override
    public void observer(String _userId, String stockid) {
        long starttime = System.currentTimeMillis();
        logger.info("服务执行----------------->用户[{}],股票[{}]:", _userId, stockid);

        String userId = _userId;
        String stockId = stockid;

        //获取用户信息
        SysUser user = new SysUser();
        user.setUserId(userId);
        user = systemUserService.queryUser(user);

        SkWarehousePositon position = skWarehousePositonMapper.selectByPrimaryKey(stockId);

        if (null == position) {
            logger.warn("股票池中没有找到stockId[{}],任务", stockId);
            return;
        }

        //实时获取股票价格
        StockInfo aimStock = queryStockPriceRealTimeSerivce.getStockInfoMap().get(
            position.getStockName());

        if (aimStock == null) {
            return;
        }

        //获取用户对 该股票 所有规则  放入map,命中则删除该规则标记位
        List<SkCustomerWarn> customerRules = customerWarnMap.get(userId + stockId);
        Map<String, Serializable> parament = new HashMap<String, Serializable>();

        parament.put("currentPrice", aimStock.getCurrentPrice());
        parament.put("wave", aimStock.getWave().abs());
        parament.put("waveValue", aimStock.getWave());

        if (null != customerRules) {
            for (SkCustomerWarn skCustomerWarn : customerRules) {
                if ("0".equals(skCustomerWarn.getStatus())) {//该规则已命中，跳到下一个规则
                    continue;
                }
                //获取规则
                SkWarnRule warnRule = rulesMap.get(skCustomerWarn.getRuleId());

                if (null == warnRule || StringUtils.isBlank(warnRule.getScripte())) {
                    break;
                }
                parament.put("exceptValue", skCustomerWarn.getValue());

                boolean result = scriptService.executeScripte(warnRule.getScripte(), parament);

                if (result) {//命中规则
                    skCustomerWarn.setStatus("0");

                    //发送短信
                    if (null != user && StringUtils.isNotBlank(user.getMobile())) {
                        //短信描述信息
                        String message = VelocityUtils.evaluate(parament, warnRule.getRuleMsg());
                        SkSendSmsLog smsLog = new SkSendSmsLog();

                        smsLog.setSmsId(UUID.randomUUID().toString().replace("-", ""));
                        smsLog.setSendMobile(user.getMobile());
                        smsLog.setSendCnt(message);
                        skSendSmsLogMapper.insert(smsLog);

                        Message msg = new Message();
                        msg.setMobile(user.getMobile());

                        Map<String, Serializable> smsParam = new HashMap<String, Serializable>();

                        smsParam.put("taskId", "ds" + stockId);
                        smsParam.put("taskName", message);
                        smsParam.put("date", DateUtils.getShortDay());
                        msg.setParament(smsParam);

                        //sendMessageService.sendMessage(msg);

                        //记录日志
                        SkWarnLog skWarnLog = new SkWarnLog();
                        skWarnLog.setId(UUID.randomUUID().toString().replace("-", ""));
                        skWarnLog.setUserId(userId);
                        skWarnLog.setStockId(stockId);
                        skWarnLog.setRuleId(skCustomerWarn.getRuleId());
                        skWarnLog.setWarnMsg(message);
                        skWarnLogMapper.insert(skWarnLog);
                    }
                }

            }
        }
        logger.info("任务耗时:{}", System.currentTimeMillis() - starttime);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        refresh();

        refreshRules();

    }

    @Override
    public void refresh() {
        //获取所有用户  自定义规则 集合
        SkCustomerWarn customerWarn = new SkCustomerWarn();
        customerWarn.setPageSize(10000);
        customerWarn.setStatus("1");

        Page<SkCustomerWarn> cusomerWarnPage = skCustomerWarnMapper.queryForPage(customerWarn);

        List<SkCustomerWarn> customerWarns = cusomerWarnPage.getDatas();

        for (SkCustomerWarn customer : customerWarns) {
            List<SkCustomerWarn> customers = customerWarnMap.get(customer.getUserId().trim()
                                                                 + customer.getStockId().trim());
            if (null == customers) {
                customers = new ArrayList<SkCustomerWarn>();
                customerWarnMap.put(customer.getUserId().trim() + customer.getStockId().trim(),
                    customers);
            }
            customers.add(customer);
        }
    }

    @Override
    public void refreshRules() {

        //获取所有规则定义
        SkWarnRule skWarnRule = new SkWarnRule();
        skWarnRule.setPageSize(10000);
        Page<SkWarnRule> rulePage = skWarnRuleMapper.queryForPage(skWarnRule);

        List<SkWarnRule> warnRules = rulePage.getDatas();

        for (SkWarnRule rule : warnRules) {
            rulesMap.put(rule.getRuleId(), rule);
        }
    }

}
