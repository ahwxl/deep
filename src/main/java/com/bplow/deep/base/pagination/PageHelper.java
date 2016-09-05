/**
 * www.bplow.com
 */
package com.bplow.deep.base.pagination;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2016年9月5日 下午10:26:35
 */
public class PageHelper {
	
	ThreadLocal<PageInfo> threadLocal = new ThreadLocal<PageInfo>();
	
	static PageHelper pageHelper = null;
	
	private PageHelper(){
		super();
	}
	
	public static synchronized  PageHelper  getPageHelper(){
		if(pageHelper == null){
			pageHelper = new PageHelper();
		}
		return pageHelper;
	}

	public void setPageInfo(PageInfo pi){
		threadLocal.set(pi);
	}
	
	public PageInfo get(){
		return threadLocal.get();
	}
	

}
