package com.bplow.deep.maintain.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.maintain.domain.AutoAppInfo;
import com.bplow.deep.maintain.service.ApplicationInfoService;
import com.bplow.deep.maintain.service.MaintainService;

@Controller
public class ApplicationInfoController {

    private Logger                 logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationInfoService applicationInfoService;
    
    @Autowired
    private MaintainService maintainService;

    @RequestMapping(value = "/app/index")
    public String index() {

        return "maintain/app-list";
    }

    @RequestMapping(value = "/app/queryForPage")
    @ResponseBody
    public Page<AutoAppInfo> applist(AutoAppInfo autoAppInfo, HttpServletRequest httpRequest) {

        HttpSession session = httpRequest.getSession();
        logger.info("sessionId:{}", session.getId());

        session.setAttribute("wxl", "汪小二");

        //autoAppInfo.setAppCode(autoAppInfo.getsSearch());
        Page<AutoAppInfo> page = applicationInfoService.queryForList(autoAppInfo);

        return page;
    }

    @RequestMapping(value = "/app/deploy")
    @ResponseBody
    public String deploy(HttpServletRequest httpRequest) {

        String str = maintainService.deploy();
        
        return str;
    }
    
    @RequestMapping(value = "/app/stop")
    @ResponseBody
    public String stop(HttpServletRequest httpRequest){
        
        
        return "";
    }
    
    @RequestMapping(value = "/app/start")
    @ResponseBody
    public String start(){
        
        
        return "";
    }
    
    @RequestMapping(value = "/app/reStart")
    @ResponseBody
    public String reStart(){
        
        
        return "";
    }

}
