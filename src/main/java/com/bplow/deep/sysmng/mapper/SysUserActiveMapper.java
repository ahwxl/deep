package com.bplow.deep.sysmng.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysUserActive;

public interface SysUserActiveMapper {
    
    Page<SysUserActive> queryForPage(SysUserActive record);

    SysUserActive selectByPrimaryKey(Integer id);
    
    SysUserActive queryActiveByUrl(String activeUrl);

    int update(SysUserActive record);

    int insert(SysUserActive record);

    int delete(Integer id);
    
}