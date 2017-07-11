package com.bplow.deep.bpm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmProcessDefined;
import com.bplow.deep.bpm.service.ProcessDefinedService;


@Controller
@RequestMapping(value = "/bpm/")
public class ProcessDefinedSetController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProcessDefinedService processDefinedService;
	
	@RequestMapping(value = "/processDefinePage")
    public String index(HttpServletRequest httpRequest, HttpServletResponse response, Model view) {

        logger.info("展示定义列表页面");

        return "bpm/set/process-defined-list";
    }
	
	@RequestMapping(value = "/processDefineList")
	@ResponseBody
    public Page<BpmProcessDefined> processDefineList(HttpServletRequest httpRequest, HttpServletResponse response, Model view,BpmProcessDefined processDefined) {

        logger.info("展示流程列表查询");
        
        Page<BpmProcessDefined> page =processDefinedService.queryProcessDefineForPage(processDefined);

        return page;
    }
	
	@RequestMapping(value = "/processDefineSetPage")
	public String processDefineSetPage(BpmProcessDefined defined){
	    
	    
	    
	    return "bpm/set/process-defined-set";
	}
	
	@RequestMapping(value = "/processDefineSet")
	@ResponseBody
    public String processDefineSet(BpmProcessDefined defined){
        
        
        
        return "bpm/set/process-defined-set";
    }

}
