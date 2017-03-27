package com.bplow.deep.authority;

import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SessionValidaterJob extends QuartzJobBean {

    private static Logger      logger = LoggerFactory.getLogger(SessionValidaterJob.class
                                          .getName() + ".quartz cluster job");

    private ApplicationContext applicationContext;

    /**
     * 从SchedulerFactoryBean注入的applicationContext.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        
        logger.info("session --------------------------");

        ValidatingSessionManager sessionManager = (ValidatingSessionManager) applicationContext.getBean("sessionManager");
        
        sessionManager.validateSessions();
        
    }

}
