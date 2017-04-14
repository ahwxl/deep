/**
 * www.bplow.com
 */
package com.bplow.deep.bpm.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;

import com.bplow.deep.authority.User;
import com.bplow.deep.stock.domain.SysUser;
import com.bplow.deep.sysmng.service.UserService;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年3月23日 下午11:26:05
 */
@Controller
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/checkLogin.do")
	@ResponseBody
    public String login(HttpServletRequest request) {
		
		String result = "login.jsp";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		Subject currentUser = SecurityUtils.getSubject();  
        try {  
            if (!currentUser.isAuthenticated()){//使用shiro来验证  
                token.setRememberMe(false);
                currentUser.login(token);//验证角色和权限
                Session session = currentUser.getSession(true);
                
                User user = userService.findByUsername(username);
                
                session.setAttribute("lgu", user);
            }  
            result = "index";//验证成功  
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = "login.do";//验证失败  
        }  
        return result;
	}
	
	
	@RequestMapping("/register.do")
	@ResponseBody
	public String doRegister(HttpServletRequest request,SysUser sysUser){
		
		sysUser.setUserId(sysUser.getUserName());
		userService.createUser(sysUser);
		
		return "success";
	}
	
	

}
