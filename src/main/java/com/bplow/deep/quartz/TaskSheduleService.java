package com.bplow.deep.quartz;

import java.util.Date;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.bplow.deep.quartz.job.ObserverJob;


@Component
public class TaskSheduleService {

    private static Logger logger = LoggerFactory.getLogger(TaskSheduleService.class);

    @Autowired
    @Qualifier("clusterQuartzScheduler")
    SchedulerFactoryBean clusterQuartzScheduler;

    public List queryTaskList() throws SchedulerException {
        JobKey jobkey = new JobKey("job1", "group1");
        List<Trigger> list = (List<Trigger>) clusterQuartzScheduler.getScheduler()
            .getTriggersOfJob(jobkey);
        for (Trigger tr : list) {
            logger.info(tr.getJobKey().getName());
        }

        return list;
    }

    /**
     * 创建表达式任务
     * @throws SchedulerException
     */
    public void createCronTask() throws SchedulerException {

        JobDetail job = JobBuilder.newJob(ObserverJob.class).withIdentity("job1", "group1")
            .build();

        CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
            .withIdentity("trigger1", "group1")
            .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();

        Date ft = clusterQuartzScheduler.getScheduler().scheduleJob(job, trigger);

        logger.info("任務創建：{0}", ft);
    }

    /**
     *
     *
     * @param triggerName
     * @param triggerGroup
     * @throws SchedulerException
     */
    public void editerCronTrigger(String triggerName, String triggerGroup)
                                                                          throws SchedulerException {

        TriggerKey tkey = new TriggerKey(triggerName, triggerGroup);
        CronTrigger newtrigger = (CronTrigger) TriggerBuilder.newTrigger()
            .withIdentity("trigger2", "group1")
            .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?")).build();

        clusterQuartzScheduler.getScheduler().rescheduleJob(tkey, newtrigger);
    }

    /**
     * 刪除任務
     *
     * @param jobname
     * @param jobgroup
     * @throws SchedulerException
     */
    public void deleteCronTask(String jobname, String jobgroup) throws SchedulerException {
        JobKey jobkey = new JobKey(jobname, jobgroup);
        Boolean result = clusterQuartzScheduler.getScheduler().deleteJob(jobkey);
    }
   

    /**
     * 暂停job
     *
     * @param jobname
     * @param jobgroup
     * @throws SchedulerException
     */
    public void pauseJob(String jobname, String jobgroup) throws SchedulerException {
        JobKey jobkey = new JobKey(jobname, jobgroup);
        clusterQuartzScheduler.getScheduler().pauseJob(jobkey);

    }
   
    /**
     * 暂停 tigger
     * @param triggerName
     * @param triggerGroup
     * @throws SchedulerException
     */
    public void pauseTrigger(String triggerName, String triggerGroup) throws SchedulerException {

        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        clusterQuartzScheduler.getScheduler().pauseTrigger(triggerKey);
    }

    /**
     * 恢复 job
     *
     * @param jobname
     * @param jobgroup
     * @throws SchedulerException
     */
    public void resumeJob(String jobname, String jobgroup) throws SchedulerException {
        JobKey jobkey = new JobKey(jobname, jobgroup);
        clusterQuartzScheduler.getScheduler().resumeJob(jobkey);
    }
   
    /**
     * 恢复触发
     *
     * @param triggerName
     * @param triggerGroup
     * @throws SchedulerException
     */
    public void resumeTrigger(String triggerName, String triggerGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        clusterQuartzScheduler.getScheduler().resumeTrigger(triggerKey);
    }

    /**
     * 立即出發任務
     *
     * @param jobname
     * @param jobgroup
     * @throws SchedulerException
     */
    public void triggerJob(String jobname, String jobgroup) throws SchedulerException {

        JobKey jobkey = new JobKey(jobname, jobgroup);
        clusterQuartzScheduler.getScheduler().triggerJob(jobkey);
    }

    public void setClusterQuartzScheduler(SchedulerFactoryBean clusterQuartzScheduler) {
        this.clusterQuartzScheduler = clusterQuartzScheduler;
    }

}

