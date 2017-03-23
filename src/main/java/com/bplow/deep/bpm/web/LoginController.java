/**
 * www.bplow.com
 */
package com.bplow.deep.bpm.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年3月23日 下午11:26:05
 */
@Controller
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/checkLogin.do")
	@ResponseBody
    public String login(HttpServletRequest request) {
		
		String result = "login.jsp";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		Subject currentUser = SecurityUtils.getSubject();  
        try {  
            System.out.println("----------------------------");  
            if (!currentUser.isAuthenticated()){//使用shiro来验证  
                token.setRememberMe(true);  
                currentUser.login(token);//验证角色和权限  
            }  
            System.out.println("result: " + result);  
            result = "index";//验证成功  
        } catch (Exception e) {
            logger.error(e.getMessage());  
            result = "login.do";//验证失败  
        }  
        return result;
		
	}
	
	

}
