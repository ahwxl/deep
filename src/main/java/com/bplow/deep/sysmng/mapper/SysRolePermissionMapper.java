package com.bplow.deep.sysmng.mapper;

import java.util.List;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysRolePermission;

public interface SysRolePermissionMapper {
	
    Page<SysRolePermission> queryForPage(SysRolePermission record);

    int update(SysRolePermission record);

    int insert(SysRolePermission record);
    
    List<SysRolePermission> queryRolePerm(String roleId);
    
    void delete(SysRolePermission record);
    
}