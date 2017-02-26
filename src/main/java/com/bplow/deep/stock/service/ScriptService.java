/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service;

import java.io.Serializable;
import java.util.Map;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月25日 下午5:39:52
 */
public interface ScriptService {
	
	public boolean executeScripte(String script,Map<String,Serializable> param);

}
