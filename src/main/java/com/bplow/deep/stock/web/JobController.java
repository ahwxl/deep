/**
 * www.bplow.com
 */
package com.bplow.deep.stock.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkScheduleTask;
import com.bplow.deep.stock.service.JobService;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月26日 下午9:24:00
 */
@Controller
public class JobController {
	
	private Logger     logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JobService jobService;
	
	@RequestMapping(value = "/job/joblist")
	public String jobPage(HttpServletRequest httpRequest, Model view){
	    HttpSession session = httpRequest.getSession();
        String wxl = (String)session.getAttribute("wxl");
        
        Subject subject =SecurityUtils.getSubject(); 
        Session shiroSession = subject.getSession();
        
		logger.info("任务列表页面:sessionId:{}:{}:",session.getId(),shiroSession.getId(),wxl);
		
		return "job/joblist";
	}
	
	@RequestMapping(value = "/job/queryJobList")
	@ResponseBody
	public Page<SkScheduleTask> queryJobList(HttpServletRequest httpRequest, Model view,SkScheduleTask task){
	    HttpSession session = httpRequest.getSession();
        String wxl = (String)session.getAttribute("wxl");
        logger.info("查询任务列表:sessionId:{}:{}",session.getId(),wxl);
        
        Subject subject =SecurityUtils.getSubject(); 
        Session shiroSession = subject.getSession();
        String userId = (String)shiroSession.getAttribute("userId");
        task.setUserId(userId);
		Page<SkScheduleTask>  page= jobService.queryScheduleTaskList(task);
		
		return page;
	}
	
	@RequestMapping(value = "/job/queryJob")
	@ResponseBody
	public SkScheduleTask queryJob(HttpServletRequest httpRequest, Model view,SkScheduleTask task){
	    HttpSession session = httpRequest.getSession();
        String wxl = (String)session.getAttribute("wxl");
        logger.info("查询任务列表:sessionId:{}:{}",session.getId(),wxl);
        
        Subject subject =SecurityUtils.getSubject(); 
        Session shiroSession = subject.getSession();
        String userId = (String)shiroSession.getAttribute("userId");
        task.setUserId(userId);
        SkScheduleTask skScheduleTask = jobService.queryJob(task);
		
		return skScheduleTask;
	}
	
	@RequestMapping(value = "/job/createJob")
	@ResponseBody
	public String createJob(SkScheduleTask task,HttpServletRequest httpRequest, Model view){
		
		logger.info("任务创建请求:{}",task);
		
		task.setStatus("1");
		jobService.createJob(task);
		
		return "创建成功！";
	}
	
	@RequestMapping(value = "/job/editerJob")
	@ResponseBody
	public String editerJob(SkScheduleTask task,HttpServletRequest httpRequest, Model view){
		
		logger.info("任务修改请求:{}",task);
		
		jobService.editerJob(task);
		
		return "修改成功！";
	}
	
	@RequestMapping(value = "/job/delJob")
	@ResponseBody
	public String delJob(SkScheduleTask task,HttpServletRequest httpRequest, Model view){
		
	    jobService.deleteJob(task);
		
		return "删除成功";
	}
	
	@RequestMapping(value = "/job/executeJob")
	@ResponseBody
	public String executeJob(SkScheduleTask task,HttpServletRequest httpRequest, Model view){
		
		boolean result = jobService.triggerJob(task);
		
		return result==true?"成功":"失败";
	}
	
	
	@RequestMapping(value = "/job/pauseJob")
    @ResponseBody
	public String pauseJob(SkScheduleTask task,HttpServletRequest httpRequest, Model view){
	    
		boolean result = jobService.pauseJob(task);
	    
	    return result==true?"成功":"失败";
	}
	
	@RequestMapping(value = "/job/resumeJob")
    @ResponseBody
	public String resumeJob(SkScheduleTask task,HttpServletRequest httpRequest, Model view){
	    
		boolean result = jobService.resumeJob(task);
	    
	    return result==true?"成功":"失败";
	}

}
