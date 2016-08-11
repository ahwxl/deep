package com.bplow.deep.authority;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CasFilter extends AuthenticatingFilter{
    
    private Logger          logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response)
                                                                                               throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ticket = httpRequest.getParameter("ticket");
        
        return new MyCasToken(ticket);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
                                                                                      throws Exception {
        return executeLogin(request, response);
    }
    
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
            Subject subject, ServletRequest request, ServletResponse response)
            throws Exception {
        issueSuccessRedirect(request, response);
        return false;
    }
    
    @Override
    protected void issueSuccessRedirect(ServletRequest request,
            ServletResponse response) throws Exception {
        String successUrl = null;
        boolean contextRelative = true;
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        if (savedRequest != null
                && savedRequest.getMethod().equalsIgnoreCase("GET")) {
            successUrl = savedRequest.getRequestUrl();
            contextRelative = false;
        }
        //logger.info("successUrl: " + successUrl);
        if (successUrl == null)
            successUrl = getSuccessUrl();
        if (successUrl == null) {
            throw new IllegalStateException(
                    "Success URL not available via saved request or via the successUrlFallback method parameter. One of these must be non-null for issueSuccessRedirect() to work.");
        } else {
            HttpServletRequest requestNew = (HttpServletRequest) (request);
            HttpServletResponse responseNew = (HttpServletResponse) (response);
            //FFTRedirectView view = new FFTRedirectView(successUrl, true, false);
            /*view.renderMergedOutputModel(null, (HttpServletRequest) (request),
                    (HttpServletResponse) (response));*/

            StringBuilder targetUrl = new StringBuilder();
            /*if (contextRelative && view.getUrl().startsWith("/"))
                targetUrl.append(requestNew.getContextPath());

            targetUrl.append(view.getUrl());
            view.appendQueryProperties(targetUrl, null, "UTF-8");*/
            
//          responseNew.sendRedirect(responseNew.encodeRedirectURL(targetUrl.toString()));
            
            responseNew.setStatus(303);
            responseNew.setHeader("Location",
                    responseNew.encodeRedirectURL(successUrl));
            return;
        }
    }
    
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
            AuthenticationException ae, ServletRequest request,
            ServletResponse response) {
        logger.info("{}",ae);
        // is user authenticated or in remember me mode ?
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch (Exception e) {
                logger.error("Cannot redirect to the default success url", e);
            }
        } else {
            try {
                WebUtils.issueRedirect(request, response, "/");
            } catch (IOException e) {
                logger.error("Cannot redirect to failure url : {}", "/",
                        e);
            }
        }
        return false;
    }

}
