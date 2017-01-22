package com.bplow.deep.bpm.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bplow.deep.bpm.service.BmpService;

@Controller
public class BmpController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
		return "bpm/processInstancePage";
	}
	
	/**
	 * 流程实例列表
	 * @return
	 */
	//@RequiresRoles("Admin")
	@RequestMapping(value = "/bpm/processInstance")
	@ResponseBody
	public String index() {
		logger.info("流程管理");

		Object user = SecurityUtils.getSubject().getPrincipal();
		Session session = SecurityUtils.getSubject().getSession(true);

		logger.info("流程管理页面:{},sessionId={}", user, session.getId());
		return "bpm/index";
	}
	
	@RequestMapping(value = "/bpm/processType")
    public String processType() {

        return "bpm/processType";
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
	public String createProcessInstancePage(){
		
		
		return "";
	}
	
	/**
	 * 启动流程
	 * @return
	 */
	@RequestMapping(value = "/bpm/createProcessInstanceAction")
	@ResponseBody
	public String createProcessInstanceAction(){
		
		return "";
	}
	
	/**
	 * 展示流程信息
	 * @return
	 */
	@RequestMapping(value = "/bpm/viewTaskPageByOne")
	public String viewTaskPageByOne(){
		
		
		return "";
	}
	
	/**
	 * 提交任务审批
	 * @return
	 */
	@RequestMapping(value = "/bpm/completeTask")
	@ResponseBody
	public String completeTask(){
		
		return "";
	}
	
	/**
	 * 展示流程及信息
	 * @return
	 */
	@RequestMapping(value = "/bpm/viewProcessInstance")
	public String viewProcessInstance(){
		
		return "";
	}
	
	/**
	 * 展示历史执行步骤
	 * @return
	 */
	@RequestMapping(value = "/bpm/viewHistoryProcessTask")
	@ResponseBody
	public String viewHistoryProcessTask(){
		
		
		return "";
	}
	

	@RequestMapping(value = "/ajax/deploy")
	@ResponseBody
	public String tree() {

		return "{\"iTotalRecords\":100,\"iTotalDisplayRecords\":10,\"aaData\": [{\"a\":123,\"b\":568},{\"a\":123,\"b\":568}]"
				+ "}";
	}

}
