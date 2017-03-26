package com.bplow.deep.sysmng.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysResource;

public interface SysResourceMapper {
	
    Page<SysResource> queryForPage(SysResource record);

    SysResource selectByPrimaryKey(String resourceId);

    int update(SysResource record);

    int insert(SysResource record);

    int delete(String resourceId);
}