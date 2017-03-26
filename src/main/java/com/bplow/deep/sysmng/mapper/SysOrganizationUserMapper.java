package com.bplow.deep.sysmng.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysOrganizationUser;

public interface SysOrganizationUserMapper {
	
    Page<SysOrganizationUser> queryForPage(SysOrganizationUser record);

    int update(SysOrganizationUser record);

    int insert(SysOrganizationUser record);
}