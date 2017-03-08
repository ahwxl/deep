package com.bplow.deep.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.bplow.deep.stock.service.SwitchDayService;

public class DaySwitchJob implements Job {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        SchedulerContext schCtx;

        try {
            schCtx = context.getScheduler().getContext();
            
            logger.info("日切任务执行");
            //String taskId = context.getJobDetail().getJobDataMap().getString("taskId");
            //String userId = context.getJobDetail().getJobDataMap().getString("userId");
            //String stockId = context.getJobDetail().getJobDataMap().getString("stockId");

            ApplicationContext appCtx = (ApplicationContext) schCtx.get("applicationContext");

            SwitchDayService service = (SwitchDayService) appCtx.getBean("switchDayService");

            service.switchDay();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

}
