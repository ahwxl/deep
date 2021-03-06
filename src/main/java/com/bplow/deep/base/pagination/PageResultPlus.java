/**
 * www.bplow.com
 */
package com.bplow.deep.base.pagination;

import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.Logger;

/**
 * @desc
 * @author wangxiaolei
 * @date 2016年9月4日 下午11:02:17
 */
@Intercepts({ @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {
	Statement.class}) })
public class PageResultPlus implements Interceptor{

	private static final Logger LOGGER = Logger.getLogger(PageResultPlus.class);
	
	private static PageHelper pageHelper;
	
	public PageResultPlus() {
		pageHelper = PageHelper.getPageHelper();
	}
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		LOGGER.debug("intercept -> invocation.target : " + invocation.getTarget());
		
		DefaultResultSetHandler drs = (DefaultResultSetHandler)invocation.getTarget();
		//Object obj = invocation.proceed();
		//Object param = invocation.getArgs()[0];
		
		//PageInfo pageInfo = pageHelper.get();
		
		/*Pagination page = new Pagination();
		page.setDatas((List)obj);
		page.setPageNum(pageInfo.getPageNo());
		page.setPageNum(pageInfo.getTotalCount());
		page.setTotals(pageInfo.getTotalCount());*/
		
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
