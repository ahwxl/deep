/**
 * www.bplow.com
 */
package com.bplow.deep.base.pagination;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2016年9月5日 下午10:38:37
 */
public class Pagination<T> implements Page<Object>{
	
    @JsonProperty("iTotalDisplayRecords")
	public int pageSize;
	
    @JsonRawValue
	public int pageNum;
	
	@JsonProperty("iTotalRecords")
	public int totals;

	@JsonProperty("aaData")
	private List<T> datas;

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getPageNum() {
		return 0;
	}

	@Override
	public int getPageTotals() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}
	
}
