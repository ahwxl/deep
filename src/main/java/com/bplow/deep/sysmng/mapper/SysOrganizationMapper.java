package com.bplow.deep.sysmng.mapper;

import java.util.List;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysOrganization;

public interface SysOrganizationMapper {
    
    Page<SysOrganization> queryForPage(SysOrganization record);

    SysOrganization selectByPrimaryKey(String organizationId);

    int update(SysOrganization record);

    int insert(SysOrganization record);

    int delete(String organizationId);
    
    List<SysOrganization> queryOrgList(SysOrganization record);
    
}