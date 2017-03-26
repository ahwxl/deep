package com.bplow.deep.sysmng.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysPermission;

public interface SysPermissionMapper {
	
    Page<SysPermission> queryForPage(SysPermission record);

    SysPermission selectByPrimaryKey(String permissionId);

    int update(SysPermission record);

    int insert(SysPermission record);

    int delete(String permissionId);
}