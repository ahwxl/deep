package com.bplow.deep.sysmng.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysRole;

public interface SysRoleMapper {
	
    Page<SysRole> queryForPage(SysRole record);

    SysRole selectByPrimaryKey(String roleId);

    int update(SysRole record);

    int insert(SysRole record);

    int delete(String roleId);
}