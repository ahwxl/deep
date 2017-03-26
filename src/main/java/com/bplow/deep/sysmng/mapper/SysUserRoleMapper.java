package com.bplow.deep.sysmng.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysUserRole;

public interface SysUserRoleMapper {
	
    Page<SysUserRole> queryForPage(SysUserRole record);

    int update(SysUserRole record);

    int insert(SysUserRole record);
}