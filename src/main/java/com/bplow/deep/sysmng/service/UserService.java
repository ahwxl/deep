package com.bplow.deep.sysmng.service;

import java.util.Set;

import com.bplow.deep.authority.User;
import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysUserRole;

public interface UserService {

    /**
     * 创建用户
     * @param user
     */
    //@Cacheable(value="manual", key="#user.userId")
    public User createUser(User user);
    
    //@CacheEvict(value = "manual", allEntries=true)
    public void deleteUser(User user);
    
    public void updateUser(User user);
    
    public void addUserRole(SysUserRole userRole,String roleIds,String delIds);
    
    public Page<User> queryUserForPage(User user);
    
    public boolean checkUserValidater(User user);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(String userId, String newPassword);

    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void correlationRoles(String userId, Long... roleIds);


    /**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void uncorrelationRoles(String userId, Long... roleIds);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    //@Cacheable(value="manual", key="#username")
    public User findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);
    
    //激活邮箱
    public boolean activeEmail(String userName);
    
    //创建激活邮箱链接
    public String createActiveEmailLink(String email);
    
    //创建重置密码链接
    public boolean createResetPwdLink(String emailAddr);
    
    //检查重置密码连接
    public boolean checkResetPwdLink(String linked);
    

}
