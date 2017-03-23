package com.bplow.deep.authority;

public interface User {
    
    void setUserId(String userId);
    
    void setUserName(String userName);
    
    void setPassword(String password);
    
    void setSalt(String salt);
    
    boolean getLocked();
    
    String getUserId();
    
    String getUserName();
    
    String getPassword();
    
    String getCredentialsSalt();

}
