/**
 * www.bplow.com
 */
package com.bplow.deep.base.dto;

import java.io.Serializable;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年4月8日 上午6:52:30
 */
public class SelectNode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6043907321709242184L;
	
	private String id;
	
	private String text;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	

}
