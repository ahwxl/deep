/**
 * www.bplow.com
 */
package com.bplow.deep.stock.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月23日 下午9:40:47
 */
public class Message {
	
	private String messageId;
	private String mobile;
	private String content;
	private Map<String,Serializable> parament;
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Map<String, Serializable> getParament() {
		return parament;
	}
	public void setParament(Map<String, Serializable> parament) {
		this.parament = parament;
	}
	
}
