package com.bplow.deep.bpm.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BmpController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @RequestMapping(value="/bpm/index")
    public String index(){
        logger.info("流程管理");
        
        Object user = SecurityUtils.getSubject().getPrincipal();
        Session session = SecurityUtils.getSubject().getSession(true);
        
        logger.info("流程管理页面:{},sessionId={}",user,session.getId());
        return "bpm/index";
    }

}
