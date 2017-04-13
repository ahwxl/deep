package com.bplow.deep.sysmng.mapper;

import java.util.Set;

import com.bplow.deep.authority.User;
import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SysUser;

public interface SysUserMapper {

    Page<User> queryForPage(SysUser record);

    SysUser selectByPrimaryKey(String userId);
    
    Set<String> queryUserPermissions(String userId);

    int update(SysUser record);

    int insert(SysUser record);

    int delete(String userId);
}