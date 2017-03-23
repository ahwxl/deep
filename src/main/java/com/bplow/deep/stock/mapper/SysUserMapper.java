package com.bplow.deep.stock.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SysUser;

public interface SysUserMapper {

    Page<SysUser> queryForPage(SysUser record);

    SysUser selectByPrimaryKey(String userId);

    int update(SysUser record);

    int insert(SysUser record);

    int deleteByPrimaryKey(String userId);
}