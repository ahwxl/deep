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
import com.bplow.deep.bpm.domain.BpmProcessDefinedSet;
import com.bplow.deep.bpm.service.ProcessDefinedService;

@Controller
@RequestMapping(value = "/bpm/")
public class ProcessDefinedSetController {

    private Logger                logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProcessDefinedService processDefinedService;

    @RequestMapping(value = "/processDefinePage")
    public String index(HttpServletRequest httpRequest, HttpServletResponse response, Model view) {

        logger.info("展示定义列表页面");

        return "bpm/set/process-defined-list";
    }

    @RequestMapping(value = "/processDefineList")
    @ResponseBody
    public Page<BpmProcessDefined> processDefineList(HttpServletRequest httpRequest,
                                                     HttpServletResponse response, Model view,
                                                     BpmProcessDefined processDefined) {

        logger.info("展示流程列表查询");

        Page<BpmProcessDefined> page = processDefinedService
            .queryProcessDefineForPage(processDefined);

        return page;
    }

    //流程设置页面
    @RequestMapping(value = "/processDefineSetPage")
    public String processDefineSetPage(Model view, BpmProcessDefinedSet defined) {

        
        
        
        view.addAttribute("defined", defined);

        return "bpm/set/process-defined-set";
    }

    @RequestMapping(value = "/processDefineSet")
    @ResponseBody
    public String processDefineSet(BpmProcessDefinedSet bpmProcessDefinedSet) {
        
        BpmProcessDefined bpmProcessDefined = new BpmProcessDefined();
        bpmProcessDefined.setProcessDefinedId(bpmProcessDefinedSet.getProcessDefinedId());
        bpmProcessDefined.setFormId(bpmProcessDefinedSet.getFormId());
        
        processDefinedService.addProcessDefined(bpmProcessDefined);

        processDefinedService.addProcessDefinitionSet(bpmProcessDefinedSet);

        return String.format("{\"responseMsg\":\"%s\"}", "成功");
    }
    
    //查询流程设置
    @RequestMapping(value = "/queryProcessDefineSet")
    @ResponseBody
    public BpmProcessDefinedSet queryProcessDefineSet(BpmProcessDefinedSet bpmProcessDefinedSet) {
        
        BpmProcessDefinedSet set =  processDefinedService.queryProcessDefinedSet(bpmProcessDefinedSet);

        return set==null?new BpmProcessDefinedSet():set;
    }

}
