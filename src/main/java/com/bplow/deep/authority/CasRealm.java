package com.bplow.deep.authority;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;

public class CasRealm extends AuthorizingRealm {
    
    private String casServerUrlPrefix;
    
    private String casService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Object shiroUser = principals.getPrimaryPrincipal();

        //查询数据，获取用户权限set集合
        info.addStringPermissions(null);
        
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        CasToken castoken = (CasToken) token;

        TicketValidator ticketValidator = new Cas20ServiceTicketValidator("");
        try {
            Assertion casAssertion = ticketValidator.validate("tikets",
                "http://192.168.89.223:8900/sso");

            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String userId = casPrincipal.getName();

        } catch (TicketValidationException e) {
        }

        return new SimpleAuthenticationInfo(null, null, "");
    }

    
    public String getCasServerUrlPrefix() {
        return casServerUrlPrefix;
    }

    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.casServerUrlPrefix = casServerUrlPrefix;
    }

    public String getCasService() {
        return casService;
    }

    public void setCasService(String casService) {
        this.casService = casService;
    }
    
    

}
