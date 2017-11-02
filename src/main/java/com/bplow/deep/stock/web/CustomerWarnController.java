package com.bplow.deep.stock.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkCustomerWarn;
import com.bplow.deep.stock.service.CustomerWarnService;

@Controller
@RequestMapping("warn")
public class CustomerWarnController {

    public Logger       logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CustomerWarnService customerWarnService;

    @RequestMapping(value = "/customerWarnPage")
    public String jobPage(HttpServletRequest httpRequest, Model view) {
        logger.info("任务列表页面:");

        return "stock/customerwarn";
    }

    @RequestMapping(value = "/customerWarnList")
    @ResponseBody
    public Page<SkCustomerWarn> queryCustomerwarn(HttpServletRequest httpRequest, Model view,
                                                  SkCustomerWarn warn) {

        Page<SkCustomerWarn> page = customerWarnService.queryCustomerWarns(warn);

        return page;
    }

    @RequestMapping(value = "/queryCustomerWarns")
    @ResponseBody
    public List<SkCustomerWarn> queryCustomerwarns(HttpServletRequest httpRequest, Model view,
                                                   SkCustomerWarn warn) {

        List<SkCustomerWarn> warns = customerWarnService.queryCustomerWarnList(warn);

        return warns;
    }

    @RequestMapping(value = "/createCustomerWarn")
    @ResponseBody
    public String addCustomerWarn(HttpServletRequest httpRequest, Model view, SkCustomerWarn warn) {

        customerWarnService.createCustomerWarn(warn);

        return "添加成功";
    }

    @RequestMapping(value = "/delCustomerWarn")
    @ResponseBody
    public String delCustomerWarn(HttpServletRequest httpRequest, Model view, SkCustomerWarn warn) {

        customerWarnService.deleteCustomerWarn(warn);

        return "删除成功";
    }

    /**
     * 初始化三条规则
     * 波动幅度101、高于102、低于103
     * 
     * @param httpRequest
     * @param view
     * @param warn
     * @return
     */
    @RequestMapping(value = "/modifyCustomerWarn")
    @ResponseBody
    public String modifyCustomerWarn(HttpServletRequest httpRequest, Model view, SkCustomerWarn warn) {

        List<SkCustomerWarn> warns = customerWarnService.queryCustomerWarnList(warn);

        String[] ruleIdArray = new String[] { "101", "102", "103" };//默认规则id

        if (warns == null || warns.size() == 0) {//初始化
            for (String ruleId : ruleIdArray) {
                SkCustomerWarn skCustomerWarn = new SkCustomerWarn();

                if (ruleId.equals("101") && warn.isWave() == true) {
                    skCustomerWarn.setStatus("1");
                    skCustomerWarn.setValue(warn.getWaveValue().doubleValue());
                } else if (ruleId.equals("102") && warn.isHigh() == true) {
                    skCustomerWarn.setStatus("1");
                    skCustomerWarn.setValue(warn.getHighValue().doubleValue());
                } else if (ruleId.equals("103") && warn.isLower() == true) {
                    skCustomerWarn.setStatus("1");
                    skCustomerWarn.setValue(warn.getLowerValue().doubleValue());
                } else {
                    skCustomerWarn.setStatus("0");
                    skCustomerWarn.setValue(0d);
                }

                skCustomerWarn.setRuleId(ruleId);
                skCustomerWarn.setUserId(warn.getUserId());
                skCustomerWarn.setStockId(warn.getStockId());

                customerWarnService.createCustomerWarn(skCustomerWarn);
            }

        } else {
            for (SkCustomerWarn customerWarn : warns) {
                if ("101".equals(customerWarn.getRuleId())) {
                    if (warn.isWave() == true) {
                        customerWarn.setStatus("1");
                        customerWarn.setValue(warn.getWaveValue().doubleValue());
                    } else {
                        customerWarn.setStatus("0");
                    }
                } else if ("102".equals(customerWarn.getRuleId())) {
                    if (warn.isHigh() == true) {
                        customerWarn.setStatus("1");
                        customerWarn.setValue(warn.getHighValue().doubleValue());
                    } else {
                        customerWarn.setStatus("0");
                    }
                } else if ("103".equals(customerWarn.getRuleId())) {
                    if (warn.isLower() == true) {
                        customerWarn.setStatus("1");
                        customerWarn.setValue(warn.getLowerValue().doubleValue());
                    } else {
                        customerWarn.setStatus("0");
                    }
                }

                customerWarnService.updateCustomerWarn(customerWarn);
            }
        }

        return "{\"responseMsg\":\"修改成功\"}";
    }

}
