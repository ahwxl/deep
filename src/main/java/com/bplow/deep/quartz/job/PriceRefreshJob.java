/**
 * www.bplow.com
 */
package com.bplow.deep.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.bplow.deep.stock.service.QueryStockPriceRealTimeSerivce;

/**
 * @desc
 * @author wangxiaolei
 * @date 2017年3月6日 下午10:34:33
 */
public class PriceRefreshJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		SchedulerContext schCtx;

		try {
			schCtx = context.getScheduler().getContext();

			ApplicationContext appCtx = (ApplicationContext) schCtx
					.get("applicationContext");

			QueryStockPriceRealTimeSerivce service = (QueryStockPriceRealTimeSerivce) appCtx
					.getBean("QueryStockPriceRealTimeSerivce");
			
			service.queryPrice("");

		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

}
