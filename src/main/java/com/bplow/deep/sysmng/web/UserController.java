/**
 * www.bplow.com
 */
package com.bplow.deep.sysmng.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.authority.User;
import com.bplow.deep.base.patchca.ValidateCode;
import com.bplow.deep.sysmng.domain.SysUser;
import com.bplow.deep.sysmng.service.UserService;

/**
 * @desc 邮箱找回密码、激活邮箱、图形验证码、修改密码
 * @author wangxiaolei
 * @date 2017年4月14日 下午9:09:06
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService    userService;

    //修改密码页面
    @RequestMapping(value = "/changePasswdPage")
    public String changePassword(HttpServletRequest httpRequest, Model view, SysUser user) {
        logger.info("修改密码");

        return "sys/user-changer-pwd";
    }

    @RequestMapping(value = "/changePasswd")
    @ResponseBody
    public String changePasswordAction(HttpServletRequest httpRequest, Model view, SysUser user) {
        logger.info("修改密码");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User loginuser = (User) session.getAttribute("lgu");

        userService.changePassword(loginuser.getUserId(), user.getPassword());

        logger.info("用户[{}]修改密码", loginuser.getUserId());

        return "{\"responseMsg\":\"success\"}";
    }

    //显示用户信息
    @RequestMapping(value = "/showProfile")
    public String showProfile(HttpServletRequest httpRequest, Model view, SysUser user) {
        logger.info("显示用户信息");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User loginuser = (User) session.getAttribute("lgu");

        User lguser = userService.findByUsername(loginuser.getUserId());

        view.addAttribute("user", lguser);

        return "sys/user-profile";
    }

    //修改用户信息
    @RequestMapping(value = "/changeProfile")
    @ResponseBody
    public String changeProfile(HttpServletRequest httpRequest, Model view, SysUser user) {
        logger.info("修改用户信息");

        userService.updateUser(user);

        return "{\"responseMsg\":\"success\"}";
    }

    //创建重置密码链接 并发送邮件
    @RequestMapping(value = "/resetPwdActive")
    @ResponseBody
    public String resetPasswdActive(HttpServletRequest httpRequest, Model view, SysUser user) {
        logger.info("修改用户信息");

        boolean result = userService.createResetPwdLink(user.getEmail());

        return String.format("{\"responseMsg\":\"%s\",\"result\":%b}",result==true?"发送成功":"发送失败",result);
    }

    //通过邮箱发送的链接   重置密码  进入重置密码页面
    @RequestMapping(value = "/resetPasswdPage/{flag}")
    public String resetPasswd(HttpServletRequest httpRequest, Model view, SysUser user,
                              @PathVariable("flag") String flag) {
        logger.info("显示用户信息");

        //交易链接的有效性
        
        boolean result = userService.checkResetPwdLink(flag);
        
        if(!result){
            view.addAttribute("errorMsg", "充值密码链接无效");
            return "sys/error";
        }

        return "sys/user-changer-pwd";
    }

    //重置密码
    @RequestMapping(value = "/resetPasswd")
    @ResponseBody
    public String resetPasswdAction(HttpServletRequest httpRequest, Model view, SysUser user) {
        logger.info("修改用户信息");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User loginuser = (User) session.getAttribute("lgu");

        userService.changePassword(loginuser.getUserId(), user.getPassword());

        logger.info("用户[{}]修改密码", loginuser.getUserId());

        return "{\"responseMsg\":\"success\"}";
    }

    @RequestMapping(value = "/sendActiveEmail")
    @ResponseBody
    public String sendActiveEmail(HttpServletRequest httpRequest, Model view, SysUser user,
                                  @RequestParam("email") String email) {
        logger.info("显示用户信息");

        userService.createResetPwdLink(email);

        return "{\"responseMsg\":\"success\"}";
    }

    //激活邮箱
    @RequestMapping(value = "/activeEmail/{flag}")
    public String activeEmail(HttpServletRequest httpRequest, Model view, SysUser user,
                              @PathVariable("flag") String flag) {
        logger.info("显示用户信息");

        //交易链接的有效性

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User loginuser = (User) session.getAttribute("lgu");

        User lguser = userService.findByUsername(loginuser.getUserId());

        view.addAttribute("user", lguser);

        return "sys/user-profile";
    }

    //校验用户id 和 邮箱 是否可用
    @RequestMapping(value = "/checkValidater")
    @ResponseBody
    public String activeEmail(HttpServletRequest httpRequest, Model view, SysUser user) {
        logger.info("显示用户信息");

        String responseMsg = null;
        if (StringUtils.isNotBlank(user.getEmail())) {
            responseMsg = "邮箱不可用";
        } else if (StringUtils.isNotBlank(user.getUserId())) {
            responseMsg = "该用户名已被使用";
        }

        boolean result = userService.checkUserValidater(user);

        return String.format("{\"responseMsg\":\"%s\",\"result\":%b}", responseMsg, result);
    }

    @RequestMapping(value = "/patchca")
    @ResponseBody
    public void patchca(HttpServletRequest httpRequest, HttpServletResponse response, Model view) {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession(true);

        ValidateCode vCode = new ValidateCode(120, 40, 5, 100);
        session.setAttribute("code", vCode.getCode());
        try {
            vCode.write(response.getOutputStream());
        } catch (IOException e) {
            logger.error("", e);
        }
    }

}
