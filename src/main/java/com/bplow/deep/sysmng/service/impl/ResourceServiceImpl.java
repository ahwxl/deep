/**
 * www.bplow.com
 */
package com.bplow.deep.sysmng.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysResource;
import com.bplow.deep.sysmng.mapper.SysResourceMapper;
import com.bplow.deep.sysmng.service.ResourceService;

/**
 * @desc 资源管理
 * @author wangxiaolei
 * @date 2017年3月26日 下午3:03:17
 */

@Service
public class ResourceServiceImpl implements ResourceService{
	
	Logger logger =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SysResourceMapper sysResourceMapper;

	@Override
	public Page<SysResource> queryResourceForPage(SysResource resource) {
		
		Page<SysResource> page = sysResourceMapper.queryForPage(resource);
		
		return page;
	}

	@Override
	public SysResource queryResouce(SysResource resource) {
		
		SysResource sysResource = sysResourceMapper.selectByPrimaryKey(resource.getResourceId());
		
		return sysResource;
	}

	@Override
	public SysResource addResoucre(SysResource resource) {
		
		sysResourceMapper.insert(resource);
		
		return null;
	}

	@Override
	public void deleteResource(SysResource resource) {
		sysResourceMapper.delete(resource.getResourceId());
	}

	@Override
	public void updateResource(SysResource resource) {
		sysResourceMapper.update(resource);
	}

}
