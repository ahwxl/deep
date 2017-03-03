package com.bplow.deep.stock.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("warn")
public class CustomerWarnController {
    
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @RequestMapping(value = "/job/joblist")
    public String jobPage(HttpServletRequest httpRequest, Model view){
        logger.info("任务列表页面:");
        
        return "job/joblist";
    }

}
