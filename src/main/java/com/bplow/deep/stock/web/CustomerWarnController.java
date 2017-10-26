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
    
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    CustomerWarnService customerWarnService;
    
    @RequestMapping(value = "/customerWarnPage")
    public String jobPage(HttpServletRequest httpRequest, Model view){
        logger.info("任务列表页面:");
        
        return "stock/customerwarn";
    }
    
    @RequestMapping(value="/customerWarnList")
    @ResponseBody
    public Page<SkCustomerWarn> queryCustomerwarn(HttpServletRequest httpRequest, Model view,SkCustomerWarn warn){
    	
    	
    	Page<SkCustomerWarn> page = customerWarnService.queryCustomerWarns(warn);
    	
    	return page;
    }
    
    @RequestMapping(value="/queryCustomerWarns")
    @ResponseBody
    public List<SkCustomerWarn> queryCustomerwarns(HttpServletRequest httpRequest, Model view,SkCustomerWarn warn){
        
        
        List<SkCustomerWarn> warns = customerWarnService.queryCustomerWarnList(warn);
        
        return warns;
    }
    
    @RequestMapping(value="/createCustomerWarn")
    @ResponseBody
    public String addCustomerWarn(HttpServletRequest httpRequest, Model view,SkCustomerWarn warn){
    	
    	customerWarnService.createCustomerWarn(warn);
    	
    	return "添加成功";
    }
    
    @RequestMapping(value="/delCustomerWarn")
    @ResponseBody
    public String delCustomerWarn(HttpServletRequest httpRequest, Model view,SkCustomerWarn warn){
    	
    	customerWarnService.deleteCustomerWarn(warn);
    	
    	return "删除成功";
    }
    
    
    @RequestMapping(value="/modifyCustomerWarn")
    @ResponseBody
    public String modifyCustomerWarn(HttpServletRequest httpRequest, Model view,SkCustomerWarn warn){
        
        customerWarnService.updateCustomerWarn(warn);
        
        return "删除成功";
    }
    

}
