package com.bplow.deep.bpm.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.ProcessInstanceInfo;
import com.bplow.deep.bpm.service.BmpService;

@Controller
public class BmpController {

    private Logger     logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BmpService bmpService;

    /**
     * 流程实例列表
     * @return
     */
    @RequestMapping(value = "/bpm/processInstancePage")
    public String indexPage() {
        logger.info("流程管理");

        Object user = SecurityUtils.getSubject().getPrincipal();
        Session session = SecurityUtils.getSubject().getSession(true);

        logger.info("流程管理页面:{},sessionId={}", user, session.getId());
        return "bpm/processInstance";
    }

    /**
     * 流程实例列表
     * @return
     */
    //@RequiresRoles("Admin")
    @RequestMapping(value = "/bpm/processInstance")
    @ResponseBody
    public Page<ProcessInstanceInfo> index(ProcessInstanceInfo processInfo) {
        logger.info("查询流水实例请求:{}", processInfo);

        Object user = SecurityUtils.getSubject().getPrincipal();
        Session session = SecurityUtils.getSubject().getSession(true);

        Page<ProcessInstanceInfo> page = bmpService.queryProcessInstanceItem(processInfo);

        logger.info("查询流水实例返回:{},sessionId={}", user, session.getId());
        return page;
    }

    /*
     * 流程类型页面
     * 
     */
    @RequestMapping(value = "/bpm/processType")
    public String processType() {

        return "bpm/processType";
    }

    /*
     * 任务列表页面
     */
    @RequestMapping(value = "/bpm/taskItemPage")
    public String taskItemPage() {

        return "bpm/processInstanceTaskItem";
    }

    /**
     * 任务列表
     * 
     * @param processInfo
     * @return
     */
    @RequestMapping(value = "/bpm/taskItem")
    @ResponseBody
    public Page<ProcessInstanceInfo> taskItem(ProcessInstanceInfo processInfo) {

        Page<ProcessInstanceInfo> page = bmpService.queryTaskItem(processInfo);

        return page;
    }

    /**
     * 流程定义页面
     * 
     * @return
     */
    @RequiresPermissions("u2:add2")
    @RequestMapping(value = "/bpm/deploy")
    public String deploy() {

        return "bpm/deploy";
    }

    /**
     * 流程部署
     * 
     * @return
     */
    @RequestMapping(value = "/bpm/deployProcess")
    public String deployProcess(@RequestParam("name") String name,
                                @RequestParam("file") MultipartFile file) {

        return "";
    }

    /**
     * 启动流程页面
     * @return
     */
    @RequestMapping(value = "/bpm/createProcessInstance")
    public String createProcessInstancePage(ProcessInstanceInfo processInfo, Model view) {

        view.addAttribute("processInfo", processInfo);

        return "bpm/createProcessInstance";
    }

    /**
     * 启动流程
     * @return
     */
    @RequestMapping(value = "/bpm/createProcessInstanceAction")
    @ResponseBody
    public String createProcessInstanceAction(ProcessInstanceInfo processInfo,
                                              HttpServletRequest httpRequest, Model view) {

        Map<String, String[]> map = httpRequest.getParameterMap();
        Map<String, Object> variables = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "--->" + entry.getValue()[0]);
            variables.put(entry.getKey(), entry.getValue()[0]);
        }

        bmpService.startProcessByKey(processInfo.getKey(), variables);

        return "{result:ok}";
    }

    /**
     * 展示流程信息
     * @return
     */
    @RequestMapping(value = "/bpm/viewTaskPageByOne")
    public String viewTaskPageByOne() {

        return "";
    }

    /*
     * 任务审核页面
     */
    @RequestMapping(value = "/bpm/taskCompletePage")
    public String completeTaskPage(ProcessInstanceInfo processInfo, Model view) {

        ProcessInstanceInfo process = bmpService.queryTask(processInfo);
        view.addAttribute("process", process);

        return "bpm/taskComplete";
    }

    /**
     * 提交任务审批
     * @return
     */
    @RequestMapping(value = "/bpm/completeTask")
    @ResponseBody
    public String completeTask(ProcessInstanceInfo processInfo, HttpServletRequest httpRequest) {

        Map<String, String[]> map = httpRequest.getParameterMap();
        Map<String, Object> taskVariable = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "--->" + entry.getValue()[0]);
            taskVariable.put(entry.getKey(), entry.getValue()[0]);
        }
        bmpService.completeTask(processInfo.getProcessInstanceId(), processInfo.getTaskId(),
            taskVariable);

        return "/bpm/processInstanceTaskItem";
    }

    /**
     * 展示流程及信息
     * @return
     */
    @RequestMapping(value = "/bpm/viewProcessInstance")
    public String viewProcessInstance() {

        return " ";
    }

    /**
     * 展示历史执行步骤
     * @return
     */
    @RequestMapping(value = "/bpm/viewHistoryProcessTask")
    @ResponseBody
    public String viewHistoryProcessTask() {

        return "";
    }

    //流程图
    @RequestMapping(value = "/bpm/viewProcessDefImage")
    public void viewProcessDefImage(ProcessInstanceInfo processInfo,
                                      HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        
        InputStream input = bmpService.getImageInputStreamById(processInfo);
        
        OutputStream output = response.getOutputStream();
        
        IOUtils.copy(input, output);
        output.flush();
        output.close();
        input.close();

    }

    @RequestMapping(value = "/ajax/deploy")
    @ResponseBody
    public String tree() {

        return "{\"iTotalRecords\":100,\"iTotalDisplayRecords\":10,\"aaData\": [{\"a\":123,\"b\":568},{\"a\":123,\"b\":568}]"
               + "}";
    }

}
