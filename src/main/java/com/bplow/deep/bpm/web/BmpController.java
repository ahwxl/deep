package com.bplow.deep.bpm.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import com.bplow.deep.authority.User;
import com.bplow.deep.base.domain.ServiceResult;
import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.base.pagination.Pagination;
import com.bplow.deep.base.utils.WebUtils;
import com.bplow.deep.bpm.domain.BpmActivity;
import com.bplow.deep.bpm.domain.BpmProcessDefined;
import com.bplow.deep.bpm.domain.BpmProcessDefinedSet;
import com.bplow.deep.bpm.domain.ProcessInstanceInfo;
import com.bplow.deep.bpm.service.BmpService;
import com.bplow.deep.bpm.service.ProcessDefinedService;

@Controller
public class BmpController {

    private Logger                logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BmpService            bmpService;

    @Autowired
    private ProcessDefinedService processDefinedService;

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
     * @throws IOException 
     */
    @RequestMapping(value = "/bpm/deployProcess")
    @ResponseBody
    public String deployProcess(HttpServletRequest httpRequest, HttpServletResponse response,
                                Model view, @RequestParam("file") MultipartFile file)
                                                                                     throws IOException {

        String fileName = file.getOriginalFilename();
        InputStream in = file.getInputStream();

        bmpService.deploy(fileName, in);

        return "";
    }

    /**
     * 启动流程页面
     * @return
     */
    @RequestMapping(value = "/bpm/createProcessInstance")
    public String createProcessInstancePage(BpmProcessDefined processDefined, Model view) {

        BpmProcessDefined bpmProcessDef = processDefinedService.queryProcessDefined(processDefined);

        String formKey = null/*bmpService.queryProcessStartForm(processInfo)*/;

        view.addAttribute("bpmProcessDef", bpmProcessDef);
        return formKey != null ? "bpm/form/" + formKey : "bpm/createProcessInstance";
    }

    /**
     * 启动流程
     * @return
     */
    @RequestMapping(value = "/bpm/createProcessInstanceAction")
    @ResponseBody
    public String createProcessInstanceAction(ProcessInstanceInfo processInfo,
                                              HttpServletRequest httpRequest, Model view) {

        User user = WebUtils.getCurrentUser();

        Map<String, String[]> map = httpRequest.getParameterMap();
        Map<String, Object> variables = new HashMap<String, Object>();
        //会签参数处理
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "--->" + entry.getValue()[0]);
            if ("assigneeList".equalsIgnoreCase(entry.getKey())) {
                List<String> asignList = new ArrayList<String>();
                for (String tmp : entry.getValue()) {
                    asignList.add(tmp);
                }
                variables.put("assigneeList", asignList);
            } else {
                variables.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        variables.put("applyUserId", user.getUserId());

        bmpService.startProcessByKey(processInfo.getProcessDefineId(), variables);

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
    
    /**
     * 接受任务
     * 
     * @param taskId
     * @return
     */
    @RequestMapping(value="/bpm/claimTask")
    @ResponseBody
    public ServiceResult claimTask(@RequestParam("taskId") String taskId){
        ServiceResult result = new ServiceResult();
        result.setResponseMessage("操作成功");
        
        User user = WebUtils.getCurrentUser();
        
        bmpService.claimTask(taskId, user.getUserId());
        
        return result;
    }

    /*
     * 任务审核页面
     */
    @RequestMapping(value = "/bpm/taskCompletePage")
    public String completeTaskPage(ProcessInstanceInfo processInfo, Model view) {

        ProcessInstanceInfo process = bmpService.queryTask(processInfo);

        //获取流程开始表单
        BpmProcessDefined bpmProcessDef = new BpmProcessDefined();
        bpmProcessDef.setProcessDefinedId(process.getProcessDefineId());

        BpmProcessDefined bpmProcessDefined = processDefinedService
            .queryProcessDefined(bpmProcessDef);

        //获取【审批表单】id
        BpmProcessDefinedSet bpmProcessDefinedSet = new BpmProcessDefinedSet();
        bpmProcessDefinedSet.setActivityId(process.getKey());
        bpmProcessDefinedSet.setProcessDefinedId(process.getProcessDefineId());

        BpmProcessDefinedSet bpmProcDefSet = processDefinedService
            .queryProcessDefinedSet(bpmProcessDefinedSet);

        view.addAttribute("bpmProcessDefined", bpmProcessDefined);
        view.addAttribute("bpmProcDefSet", bpmProcDefSet);
        view.addAttribute("process", process);
        return "bpm/taskComplete";
    }

    /**
     * 提交任务审批
     * @return
     */
    @RequestMapping(value = "/bpm/completeTask")
    @ResponseBody
    public ServiceResult completeTask(ProcessInstanceInfo processInfo,
                                      HttpServletRequest httpRequest) {

        Map<String, String[]> map = httpRequest.getParameterMap();
        Map<String, Object> taskVariable = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "--->" + entry.getValue()[0]);
            if ("true".equalsIgnoreCase(entry.getValue()[0])) {
                taskVariable.put(entry.getKey(), true);
            } else if ("false".equalsIgnoreCase(entry.getValue()[0])) {
                taskVariable.put(entry.getKey(), false);
            } else {
                taskVariable.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        ServiceResult result = bmpService.completeTask(processInfo.getProcessInstanceId(),
            processInfo.getTaskId(), taskVariable);

        return result;
    }

    /**
     * 展示流程及信息
     * @return
     */
    @RequestMapping(value = "/bpm/viewProcessInstance")
    public String viewProcessInstance(ProcessInstanceInfo processInstanceInfo) {

        return "";
    }

    /**
     * 展示历史执行步骤
     * @return
     */
    @RequestMapping(value = "/bpm/viewHistoryProcessTask")
    @ResponseBody
    public Page<ProcessInstanceInfo> viewHistoryProcessTask(String processInstanceId) {

        List<ProcessInstanceInfo> list = bmpService.getHistoryProcess(processInstanceId);

        Page<ProcessInstanceInfo> page = new Pagination<ProcessInstanceInfo>();
        page.setDatas(list);
        page.setPageSize(100);
        page.setTotals(list.size());

        return page;
    }

    //流程图
    @RequestMapping(value = "/bpm/viewProcessDefImage")
    public void viewProcessDefImage(ProcessInstanceInfo processInfo,
                                    HttpServletRequest httpRequest, HttpServletResponse response)
                                                                                                 throws IOException {
        response.setContentType("image/jpeg");
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

    //查询流程定义 所有活动
    @RequestMapping(value = "/bpm/queryProcessActivity")
    @ResponseBody
    public List<BpmActivity> queryActivity(HttpServletRequest httpRequest,
                                           String processDefinitionId) {

        List<BpmActivity> list = bmpService.queryAllActivity(processDefinitionId);

        return list;
    }

    @RequestMapping(value = "/bpm/queryFormValuesByProcessId")
    @ResponseBody
    public Map<String, Object> queryFormValue(@RequestParam("processInstanceId") String processInstanceId) {

        Map<String, Object> map = bmpService.queryFormValuesByProcessId(processInstanceId);

        return map;
    }

}
