/**
 * www.bplow.com
 */
package com.bplow.deep.sysmng.service;

import java.util.List;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysRole;
import com.bplow.deep.sysmng.domain.SysRolePermission;

/**
 * @desc 角色
 * @author wangxiaolei
 * @date 2017年3月26日 下午3:18:53
 */
public interface RoleService {

	public Page<SysRole> queryRoleForPage(SysRole role);

	public SysRole queryRole(SysRole role);

	public SysRole addRole(SysRole role);

	public void deleteRole(SysRole role);

	public void updateRole(SysRole role);
	
	public List<SysRolePermission> queryRolePerms(String roleId);
	
	public void addRolePerm(String roleId,String permIds,String delIds);
	
	public void delRolePerm(SysRolePermission rolePerm);
	

}
