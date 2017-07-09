/**
 * www.bplow.com
 */
package com.bplow.deep.bpm.service.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmProcessDefined;
import com.bplow.deep.bpm.mapper.BpmProcessDefinedMapper;
import com.bplow.deep.bpm.service.ProcessDefinedService;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年7月9日 下午9:26:37
 */
public class ProcessDefinedServiceImpl implements ProcessDefinedService{
	
	
	@Autowired
	private BpmProcessDefinedMapper bpmProcessDefinedMapper;

	@Override
	public Page<BpmProcessDefined> queryProcessDefineForPage(
			BpmProcessDefined processDefined) {
		
		Page<BpmProcessDefined> page = bpmProcessDefinedMapper.queryForPage(processDefined);
		
		return page;
	}

	@Override
	public BpmProcessDefined queryProcessDefined(
			BpmProcessDefined processDefined) {
		
		BpmProcessDefined bpmProcessDefined = bpmProcessDefinedMapper.selectByPrimaryKey(processDefined.getId());
		
		return bpmProcessDefined;
	}

	@Override
	public String deployProcess(String name, InputStream in) {
		
		
		
		return null;
	}

	@Override
	public void updateProcessDefined(BpmProcessDefined processDefined) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProcessDefined(BpmProcessDefined bpmProcessDefined) {
		// TODO Auto-generated method stub
		
	}

}
