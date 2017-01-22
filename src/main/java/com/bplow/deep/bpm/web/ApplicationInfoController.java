package com.bplow.deep.bpm.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.base.pagination.Pagination;
import com.bplow.deep.bpm.domain.AutoAppInfo;
import com.bplow.deep.bpm.service.ApplicationInfoService;


@Controller
public class ApplicationInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ApplicationInfoService applicationInfoService;
    
    @RequestMapping(value="/app/index")
    public String index(){
        
        return "";
    }
    
    @RequestMapping(value="/app/queryForPage")
    @ResponseBody
    public Page applist(AutoAppInfo autoAppInfo){
     
        autoAppInfo.setAppCode(autoAppInfo.getsSearch());
        Page page = applicationInfoService.queryForList(autoAppInfo);
        
        return page;
    }
    
    
}
