/**
 * www.bplow.com
 */
package com.bplow.deep.base.jackson;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.bplow.deep.base.utils.DateUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年3月9日 下午9:57:39
 */
public class JsonHelper {
	
	public static String toString(Object obj) throws JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		String userMapJson = objectMapper.writeValueAsString(obj);
		return userMapJson;
	}
	
	public static void main(String[] args) {
		Map<String,Serializable> smsParam = new HashMap<String,Serializable>();
		smsParam.put("taskId", "阿斯蒂芬是到");
		smsParam.put("taskName", "33");
		smsParam.put("date", "ddf");
		
		try {
			System.out.println(JsonHelper.toString(smsParam));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
