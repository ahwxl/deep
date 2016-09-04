/**
 * www.bplow.com
 */
package com.bplow.deep.base.pagination;

import java.sql.Statement;
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
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		LOGGER.debug("intercept -> invocation.target : " + invocation.getTarget());
		
		DefaultResultSetHandler drs = (DefaultResultSetHandler)invocation.getTarget();
		//drs.
		
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}

}
