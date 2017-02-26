/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import java.io.Serializable;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bplow.deep.stock.service.ScriptService;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月25日 下午5:40:56
 */
@Service
public class ScriptServiceImpl implements ScriptService{
	
	private Logger           logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean executeScripte(String script,Map<String,Serializable> parament) {
		
		OgnlContext context = (OgnlContext) Ognl.createDefaultContext(null);
		Object root = null;
		boolean result = false;
		
        for (Map.Entry<String, Serializable> entry : parament.entrySet()) {
        	context.put(entry.getKey(), entry.getValue());
        }

		try {
            Object node = Ognl.compileExpression(context, root, script);
            //node =Ognl.parseExpression(expression);
            
            result =(Boolean) Ognl.getValue( node, context);
            
            
            System.out.println(result);
        } catch (Exception e) {
            logger.error("", e);
        }
		
		return result;
	}

}
