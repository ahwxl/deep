package com.bplow.deep.sysmng.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.authority.PasswordHelper;
import com.bplow.deep.authority.User;
import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SysUser;
import com.bplow.deep.sysmng.domain.SysUserRole;
import com.bplow.deep.sysmng.mapper.SysUserMapper;
import com.bplow.deep.sysmng.mapper.SysUserRoleMapper;
import com.bplow.deep.sysmng.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper     sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private PasswordHelper    passwordHelper;

    public void setPasswordHelper(PasswordHelper passwordHelper) {
        this.passwordHelper = passwordHelper;
    }

    /**
     * 创建用户
     * @param user
     */
    public User createUser(User user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        user.setStatus("1");
        sysUserMapper.insert((SysUser) user);
        return null;
    }

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(String userId, String newPassword) {
        SysUser user = sysUserMapper.selectByPrimaryKey(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        sysUserMapper.update(user);
    }

    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void correlationRoles(String userId, Long... roleIds) {
        //userDao.correlationRoles(userId, roleIds);
    }

    /**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void uncorrelationRoles(String userId, Long... roleIds) {
        //userDao.uncorrelationRoles(userId, roleIds);
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return sysUserMapper.selectByPrimaryKey(username);
    }

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String userId) {
        
        Set<String> roles = sysUserRoleMapper.queryUserRole(userId);
        
        return roles;
    }

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String userId) {
        
        Set<String> permissions = sysUserMapper.queryUserPermissions(userId);
        return permissions;
    }

    @Override
    public void deleteUser(User user) {
        sysUserMapper.delete(user.getUserId());
    }

    @Override
    public Page<User> queryUserForPage(User user) {

        Page<User> users = this.sysUserMapper.queryForPage((SysUser) user);

        return users;
    }

    @Override
    public void updateUser(User user) {
        this.sysUserMapper.update((SysUser) user);
    }

    @Override
    public void addUserRole(SysUserRole userRole, String roleIds, String delIds) {

        String[] roleArray = roleIds.split(",");

        String[] delPermArray = delIds.split(",");
        List<String> delList = Arrays.asList(delPermArray);

        if (delList.size() > 0) {
            SysUserRole record = new SysUserRole();
            record.setUserId(userRole.getUserId());
            record.setRoleIds(delList);

            sysUserRoleMapper.delete(record);
        }
        for (String roleId : roleArray) {
            if (StringUtils.isBlank(roleId)) {
                continue;
            }
            SysUserRole userRoleTmp = new SysUserRole();
            userRoleTmp.setUserId(userRole.getUserId());
            userRoleTmp.setRoleId(roleId);

            sysUserRoleMapper.insert(userRoleTmp);
        }

    }

	@Override
	public boolean activeEmail(String userName) {
		
		return false;
	}

	@Override
	public String createActiveEmailLink(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createResetPwdLink(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

}