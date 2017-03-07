package com.bplow.deep.stock.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkWarnRule;
import com.bplow.deep.stock.service.WarnRuleService;

@Controller
@RequestMapping("warn")
public class WarnRuleController {
    
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    WarnRuleService warnRuleService;
    
    @RequestMapping(value = "/warnRulePage")
    public String jobPage(HttpServletRequest httpRequest, Model view){
        logger.info("任务列表页面:");
        
        return "stock/warnRule";
    }
    
    @RequestMapping(value="/warnRuleList")
    @ResponseBody
    public Page<SkWarnRule> queryCustomerwarn(HttpServletRequest httpRequest, Model view,SkWarnRule rule){
    	
    	
    	Page<SkWarnRule> page = warnRuleService.queryWarnRules(rule);
    	
    	return page;
    }
    
    @RequestMapping(value="/createWarnRule")
    @ResponseBody
    public String addCustomerWarn(HttpServletRequest httpRequest, Model view,SkWarnRule rule){
    	
    	warnRuleService.createWarnRule(rule);
    	
    	return "添加成功";
    }
    
    @RequestMapping(value="/delWarnRule")
    @ResponseBody
    public String delCustomerWarn(HttpServletRequest httpRequest, Model view,SkWarnRule rule){
    	
    	warnRuleService.deleteWarnRule(rule);
    	
    	return "删除成功";
    }

}
