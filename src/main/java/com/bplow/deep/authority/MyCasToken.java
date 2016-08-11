package com.bplow.deep.authority;

import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class MyCasToken implements RememberMeAuthenticationToken{

    /**  */
    private static final long serialVersionUID = 3371535073623022379L;

    private String ticket = null;
    
    // the user identifier
    private String userId = null;
    
    // is the user in a remember me mode ?
    private boolean isRememberMe = false;
    
    public MyCasToken(String ticket) {
        super();
        this.ticket = ticket;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return ticket;
    }

    @Override
    public boolean isRememberMe() {
        return isRememberMe;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRememberMe(boolean isRememberMe) {
        this.isRememberMe = isRememberMe;
    }
    
}
