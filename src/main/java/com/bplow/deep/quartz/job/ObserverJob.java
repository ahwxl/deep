package com.bplow.deep.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.bplow.deep.stock.service.ObserverService;

public class ObserverJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
    	SchedulerContext schCtx;
    	
		try {
			schCtx = context.getScheduler().getContext();
			//String taskId = context.getJobDetail().getJobDataMap().getString("taskId");
			String userId = context.getJobDetail().getJobDataMap().getString("userId");
			String stockId = context.getJobDetail().getJobDataMap().getString("stockId");
			
			ApplicationContext appCtx = (ApplicationContext)schCtx.get("applicationContext");
			
			ObserverService service = (ObserverService)appCtx.getBean("observerService");
			
			service.observer(userId,stockId);
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    	
    	
    	
    }

}
