/**
 * www.bplow.com
 */
package com.bplow.deep.sysmng.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysPermission;
import com.bplow.deep.sysmng.domain.SysPermissionResource;
import com.bplow.deep.sysmng.mapper.SysPermissionMapper;
import com.bplow.deep.sysmng.mapper.SysPermissionResourceMapper;
import com.bplow.deep.sysmng.service.PermissionService;

/**
 * @desc 权限管理
 * @author wangxiaolei
 * @date 2017年3月26日 下午3:11:37
 */
@Service
public class PermissionServiceImpl implements PermissionService{
	
	Logger logger =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SysPermissionMapper sysPermissionMapper;
	
	@Autowired
	SysPermissionResourceMapper sysPermissionResourceMapper;

	@Override
	public Page<SysPermission> queryPermissionForPage(SysPermission permission) {
		
		Page<SysPermission> page = sysPermissionMapper.queryForPage(permission);
		
		return page;
	}

	@Override
	public SysPermission queryPermission(SysPermission permission) {
		
		SysPermission sysPermission = sysPermissionMapper.selectByPrimaryKey(permission.getPermissionId());
		
		return sysPermission;
	}

	@Override
	public SysPermission addPermission(SysPermission permission) {
		
		sysPermissionMapper.insert(permission);
		
		return null;
	}

	@Override
	public void deletePermission(SysPermission permission) {
		sysPermissionMapper.delete(permission.getPermissionId());
	}

	@Override
	public void updatePermission(SysPermission permission) {
		sysPermissionMapper.update(permission);
	}

	@Override
	public List<SysPermissionResource> queryPermRes(String permissionId) {
		
		List<SysPermissionResource> permRes = sysPermissionResourceMapper.queryPermRes(permissionId);
		
		return permRes;
	}

	@Override
	public void addPermRes(String permissionId, String resourceIds,String delPermIds) {

		String[] resArray = resourceIds.split(",");
		String[] permIds  = delPermIds.split(",");
		
		List<String> permIdsList = Arrays.asList(permIds);
		
		SysPermissionResource delPermRes = new SysPermissionResource();
		delPermRes.setDelIds(permIdsList);
		
		sysPermissionResourceMapper.delete(delPermRes);
		
		for(String resId : resArray){
			SysPermissionResource permRes = new SysPermissionResource();
			permRes.setPermissionId(permissionId);
			permRes.setResourceId(resId);
			permRes.setGmtModify(new Date());
			
			sysPermissionResourceMapper.insert(permRes);
		}
		
		
		
	}

	@Override
	public void delPermRes(String permissionId, String resourceId) {

	    SysPermissionResource delPermRes = new SysPermissionResource();
	    
		sysPermissionResourceMapper.delete(delPermRes);
	}

}
