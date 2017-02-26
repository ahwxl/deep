/**
 * www.bplow.com
 */
package com.bplow.deep.base.pagination;

import java.util.List;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2016年9月5日 下午10:38:19
 */
public interface Page<T> {
	
	int getPageSize();
	
	int getPageNum();
	
	int getTotals();
	
	void setPageNum(int pageNum);
	
	void setPageSize(int pageSize);
	
	void setTotals(int totals);
	
	void setDatas(List<T> datas);
	
	List<T> getDatas();

}
