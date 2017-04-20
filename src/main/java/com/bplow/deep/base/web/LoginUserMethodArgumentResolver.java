package com.bplow.deep.base.web;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bplow.deep.authority.User;
import com.bplow.deep.sysmng.domain.SysUser;

public class LoginUserMethodArgumentResolver implements HandlerMethodArgumentResolver{

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        
    	LoginUser requestParamAnnot = parameter.getParameterAnnotation(LoginUser.class);
        if (requestParamAnnot != null) {
            if (SysUser.class.isAssignableFrom(parameter.getParameterType())) {
                return !StringUtils.hasText(requestParamAnnot.value());
            }
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
                                                                                                  throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = null;
        if (null != session) {
            user = (User) session.getAttribute("lgu");
        }
        
        return user;
    }

}
