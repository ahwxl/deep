/**
 * www.bplow.com
 */
package com.bplow.deep.sysmng.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysRole;
import com.bplow.deep.sysmng.domain.SysRolePermission;
import com.bplow.deep.sysmng.mapper.SysRoleMapper;
import com.bplow.deep.sysmng.mapper.SysRolePermissionMapper;
import com.bplow.deep.sysmng.service.RoleService;

/**
 * @desc
 * @author wangxiaolei
 * @date 2017年3月26日 下午3:21:31
 */
@Service
public class RoleServiceImpl implements RoleService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SysRoleMapper sysRoleMapper;
	
	@Autowired
	SysRolePermissionMapper sysRolePermissionMapper;

	@Override
	public Page<SysRole> queryRoleForPage(SysRole role) {
		Page<SysRole> page = sysRoleMapper.queryForPage(role);
		return page;
	}

	@Override
	public SysRole queryRole(SysRole role) {

		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(role.getRoleId());

		return sysRole;
	}

	@Override
	public SysRole addRole(SysRole role) {

		sysRoleMapper.insert(role);

		return null;
	}

	@Override
	public void deleteRole(SysRole role) {

		sysRoleMapper.delete(role.getRoleId());

	}

	@Override
	public void updateRole(SysRole role) {

		sysRoleMapper.update(role);

	}

	@Override
	public List<SysRolePermission> queryRolePerms(String roleId) {

		List<SysRolePermission> rolePerm = sysRolePermissionMapper
				.queryRolePerm(roleId);

		return rolePerm;
	}

	@Override
	public void addRolePerm(String roleId, String permIds,String delIds) {
		String[] permArray = permIds.split(",");
		
		String[] delPermArray = delIds.split(",");
		List<String> delList = Arrays.asList(delPermArray);
		
		
		if(delList.size() > 0){
		    SysRolePermission record = new SysRolePermission();
	        record.setRoleId(roleId);
	        record.setPermissionIdList(delList);
	        
	        sysRolePermissionMapper.delete(record);
		}
		for(String permissionId : permArray){
		    if(StringUtils.isBlank(permissionId)){
		        continue;
		    }
			SysRolePermission rolePerm = new SysRolePermission();
			rolePerm.setPermissionId(permissionId);
			rolePerm.setRoleId(roleId);
			
			sysRolePermissionMapper.insert(rolePerm);
		}

	}

	@Override
	public void delRolePerm(SysRolePermission rolePerm) {
		
		sysRolePermissionMapper.delete(rolePerm);

	}

}
