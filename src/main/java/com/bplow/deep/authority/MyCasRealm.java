package com.bplow.deep.authority;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCasRealm extends AuthorizingRealm {

    private Logger                      logger = LoggerFactory.getLogger(this.getClass());

    private String                      casServerUrlPrefix;

    private String                      casService;

    private Cas20ServiceTicketValidator ticketValidator;

    public MyCasRealm() {
        setAuthenticationTokenClass(MyCasToken.class);
    }

    @Override
    protected void onInit() {
        super.onInit();
        ensureTicketValidator();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("获取用户授权信息：{}", principals);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Object shiroUser = principals.getPrimaryPrincipal();

        //查询数据，获取用户权限set集合
        Set<String> permissions = new HashSet<String>();
        permissions.add("user:delete");
        permissions.add("user:add");
        info.addRole("ROLE_USER");
        info.addStringPermissions(permissions);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        logger.info("认证用户请求参数：{}", token);
        MyCasToken castoken = (MyCasToken) token;
        String ticket = (String) castoken.getCredentials();

        SimpleAuthenticationInfo authentiacationInfo = null;
        try {
            Assertion casAssertion = ticketValidator.validate((String) castoken.getCredentials(),
                this.casService);

            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String userId = casPrincipal.getName();

            Map<String, Object> attributes = casPrincipal.getAttributes();

            String name = (String) attributes.get("name");
            String uId = (String) attributes.get("userId");
            String password = (String) attributes.get("password");

            ShiroUser user = new ShiroUser(100l, userId, name, password);
            authentiacationInfo = new SimpleAuthenticationInfo(user, ticket, getName());
        } catch (TicketValidationException e) {
            
            logger.info("{}", e);
        }
        return authentiacationInfo;

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

    protected TicketValidator ensureTicketValidator() {
        if (this.ticketValidator == null) {
            this.ticketValidator = createTicketValidator();
        }
        //this.ticketValidator.set(new MyHostnameVerifier());
        return this.ticketValidator;
    }

    protected Cas20ServiceTicketValidator createTicketValidator() {
        String urlPrefix = getCasServerUrlPrefix();
        return new Cas20ServiceTicketValidator(urlPrefix);
    }

}
