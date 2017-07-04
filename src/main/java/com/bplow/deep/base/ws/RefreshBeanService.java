package com.bplow.deep.base.ws;

import javax.jws.WebService;

@WebService(name = "RefreshBeanService", targetNamespace = WsConstants.NS)
public interface RefreshBeanService {
    
    public void refreshBeanByName(String name);
    
    public void print(String name);

}
