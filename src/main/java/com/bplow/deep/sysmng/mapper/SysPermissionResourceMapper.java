package com.bplow.deep.sysmng.mapper;

import java.util.List;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysPermissionResource;

public interface SysPermissionResourceMapper {
	
    Page<SysPermissionResource> queryForPage(SysPermissionResource record);
    
    List<SysPermissionResource> queryPermRes(String permissionId);

    int update(SysPermissionResource record);

    int insert(SysPermissionResource record);
    
    int delete(String permissionId, String resourceId);
}