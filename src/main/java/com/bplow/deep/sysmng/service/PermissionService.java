/**
 * www.bplow.com
 */
package com.bplow.deep.sysmng.service;

import java.util.List;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysPermission;
import com.bplow.deep.sysmng.domain.SysPermissionResource;

/**
 * @desc
 * @author wangxiaolei
 * @date 2017年3月26日 下午3:10:04
 */
public interface PermissionService {

	public Page<SysPermission> queryPermissionForPage(SysPermission permission);

	public SysPermission queryPermission(SysPermission permission);

	public SysPermission addPermission(SysPermission permission);

	public void deletePermission(SysPermission permission);

	public void updatePermission(SysPermission permission);
	
	//查询权限对应的资源
	public List<SysPermissionResource> queryPermRes(String permissionId);
	
	//添加权限对应的资源
	public void addPermRes(String permissionId,String resourceIds,String delPermIds);
	
	//删除权限对应的资源
	public void delPermRes(String permissionId,String resourceIds);

}
