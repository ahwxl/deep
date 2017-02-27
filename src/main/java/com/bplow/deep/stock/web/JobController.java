/**
 * www.bplow.com
 */
package com.bplow.deep.stock.web;

import javax.servlet.http.HttpServletRequest;

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
		logger.info("任务列表页面:");
		
		return "job/joblist";
	}
	
	@RequestMapping(value = "/job/queryJobList")
	@ResponseBody
	public Page<SkScheduleTask> queryJobList(HttpServletRequest httpRequest, Model view,SkScheduleTask task){
		
		Page<SkScheduleTask>  page= jobService.queryScheduleTaskList(task);
		
		return page;
	}
	
	@RequestMapping(value = "/job/createJob")
	@ResponseBody
	public String createJob(SkScheduleTask task,HttpServletRequest httpRequest, Model view){
		
		jobService.createJob(task);
		
		return "job/joblist";
	}
	
	
	@RequestMapping(value = "/job/delJob")
	@ResponseBody
	public String delJob(HttpServletRequest httpRequest, Model view){
		
		
		return "";
	}
	
	@RequestMapping(value = "/job/pauseJob")
    @ResponseBody
	public String pauseJob(SkScheduleTask task,HttpServletRequest httpRequest, Model view){
	    
	    
	    return "";
	}

}
