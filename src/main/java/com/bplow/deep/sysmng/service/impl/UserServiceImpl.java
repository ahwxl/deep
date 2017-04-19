package com.bplow.deep.sysmng.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bplow.deep.authority.PasswordHelper;
import com.bplow.deep.authority.User;
import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysUser;
import com.bplow.deep.sysmng.domain.SysUserActive;
import com.bplow.deep.sysmng.domain.SysUserRole;
import com.bplow.deep.sysmng.mapper.SysUserActiveMapper;
import com.bplow.deep.sysmng.mapper.SysUserMapper;
import com.bplow.deep.sysmng.mapper.SysUserRoleMapper;
import com.bplow.deep.sysmng.service.SendMailService;
import com.bplow.deep.sysmng.service.UserService;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper       sysUserMapper;

    @Autowired
    private SysUserActiveMapper sysUserActiveMapper;

    @Autowired
    private SysUserRoleMapper   sysUserRoleMapper;

    @Autowired
    private PasswordHelper      passwordHelper;

    @Autowired
    SendMailService             sendMailService;

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
    public boolean activeEmail(String activeUrl) {
        boolean result = false;
        SysUserActive record = sysUserActiveMapper.queryActiveByUrl(activeUrl);

        if (null == record) {
            return result;
        }

        sysUserMapper.activeEmail(record.getUserId());
        result = true;

        return result;
    }

    @Override
    public String createActiveEmailLink(String email) {

        String activeFlag = UUID.randomUUID().toString().replace("-", "");

        User user = sysUserMapper.selectByPrimaryKey(email);

        if (null == user) {
            return "用户不存在";
        }
        SysUserActive record = new SysUserActive();
        record.setUserId(user.getUserId());
        record.setStatus("0");
        record.setActiveType("0");
        record.setActiveUrl(activeFlag);

        sysUserActiveMapper.insert(record);

        //发送邮件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("toEmail", user.getEmail());
        map.put("url", "http://localhost:8181/user/activeEmail/" + activeFlag);

        String content = sendMailService.getEmailCnt("emailcxt.vm", map);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("tenement_admin@163.com");
        msg.setSubject("[执行结果通知]");
        msg.setText(content);
        msg.setTo(new String[] { "wangxiaolei@shfft.com" });

        sendMailService.sendMail(msg);

        return "";
    }

    @Override
    public boolean createResetPwdLink(String email) {
        boolean result = false;

        if (StringUtils.isBlank(email)) {
            return result;
        }
        
        User user = sysUserMapper.selectByPrimaryKey(email);
        
        if(null == user){
            return result;
        }

        String flag = UUID.randomUUID().toString().replace("-", "");
        
        SysUserActive record = new SysUserActive();
        record.setUserId(user.getUserId());
        record.setStatus("0");
        record.setActiveType("0");
        record.setActiveUrl(flag);

        sysUserActiveMapper.insert(record);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("toEmail", email);
        map.put("url", "http://www.techwellglobal.com/deep/user/resetPasswdPage/" + flag);

        String content = sendMailService.getEmailCnt("emailcxt.vm", map);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("tenement_admin@163.com");
        msg.setSubject("[执行结果通知]");
        msg.setText(content);
        msg.setTo(new String[] { email });

        sendMailService.sendMail(msg);
        result = true;

        return result;
    }

    @Override
    public boolean checkUserValidater(User user) {

        boolean result = false;
        User usertmp = sysUserMapper.queryUser(user);

        if (null != usertmp) {
            result = true;
        }

        return result;
    }

    @Override
    public boolean checkResetPwdLink(String linked) {
        
        boolean result = false;
        
        SysUserActive record = sysUserActiveMapper.queryActiveByUrl(linked);

        if (null == record) {
            return result;
        }
        result = true;
        
        return result;
    }

}