package com.bplow.deep.base.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.bplow.deep.authority.User;

public class WebUtils {

    public static User getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = null;
        if (null != session) {
            user = (User) session.getAttribute("lgu");
        }
        return user;
    }

}
