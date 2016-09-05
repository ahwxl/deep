/**
 * www.bplow.com
 */
package com.bplow.deep.base.pagination;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2016年9月5日 下午10:38:19
 */
public interface Page<T> {
	
	int getPageSize();
	
	int getPageNum();
	
	int getPageTotals();

}
