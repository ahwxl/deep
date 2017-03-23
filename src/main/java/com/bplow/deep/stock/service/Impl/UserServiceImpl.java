package com.bplow.deep.stock.service.Impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.authority.PasswordHelper;
import com.bplow.deep.authority.User;
import com.bplow.deep.stock.domain.SysUser;
import com.bplow.deep.stock.mapper.SysUserMapper;
import com.bplow.deep.stock.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    private PasswordHelper passwordHelper;

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
        sysUserMapper.insert((SysUser)user);
        return null;
    }

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(String userId, String newPassword) {
        SysUser user =sysUserMapper.selectByPrimaryKey(userId);
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
    public Set<String> findRoles(String username) {
        return null;
    }

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username) {
        return null;
    }

}