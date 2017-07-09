/**
 * www.bplow.com
 */
package com.bplow.deep.bpm.service;

import java.io.InputStream;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmProcessDefined;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年7月9日 下午9:26:17
 */
public interface ProcessDefinedService {
	
	//流程定义列表
	Page<BpmProcessDefined> queryProcessDefineForPage(BpmProcessDefined processDefined);

	BpmProcessDefined queryProcessDefined(BpmProcessDefined processDefined);
	
	//部署流程
	String deployProcess(String name,InputStream in);
	
	//设置表单
	void updateProcessDefined(BpmProcessDefined processDefined);
	
	void addProcessDefined(BpmProcessDefined bpmProcessDefined);
	
	//设置角色、用户id
	
	
}
