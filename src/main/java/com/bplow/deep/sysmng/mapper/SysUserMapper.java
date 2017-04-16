package com.bplow.deep.sysmng.mapper;

import java.util.Set;

import com.bplow.deep.authority.User;
import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysUser;

public interface SysUserMapper {

    Page<User> queryForPage(SysUser record);

    SysUser selectByPrimaryKey(String userId);
    
    User queryUser(User user);
    
    Set<String> queryUserPermissions(String userId);

    int update(SysUser record);

    int insert(SysUser record);

    int delete(String userId);
    
}