package com.bplow.deep.sysmng.mapper;

import java.util.Set;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysUserRole;

public interface SysUserRoleMapper {
	
    Page<SysUserRole> queryForPage(SysUserRole record);
    
    Set<String> queryUserRole(String userId);

    int update(SysUserRole record);

    int insert(SysUserRole record);
    
    int delete(SysUserRole record);
}