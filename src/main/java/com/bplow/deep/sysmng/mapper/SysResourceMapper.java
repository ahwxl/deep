package com.bplow.deep.sysmng.mapper;

import java.util.List;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysResource;

public interface SysResourceMapper {
	
    Page<SysResource> queryForPage(SysResource record);

    SysResource selectByPrimaryKey(String resourceId);
    
    List<SysResource> queryResource(SysResource res);

    int update(SysResource record);

    int insert(SysResource record);

    int delete(String resourceId);
}