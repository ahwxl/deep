package com.bplow.deep.base.ws;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bplow.deep.base.utils.ReloadBeanService;

@WebService(serviceName = "RefreshBeanService", endpointInterface = "com.bplow.deep.base.ws.RefreshBeanService", targetNamespace = WsConstants.NS)
public class RefreshBeanByNameImpl implements RefreshBeanService {

    private static Logger logger = LoggerFactory.getLogger(RefreshBeanByNameImpl.class);
    
    @Autowired
    private ReloadBeanService reloadBeanService;
    
    @Override
    public void refreshBeanByName(String name) {
        
        logger.info("获取刷新请求:{}",name);
        
        try {
            reloadBeanService.fresh(name);
        } catch (Exception e) {
            logger.error("刷新bean名称:{}",name, e);
        }
        
        System.out.println(name);
    }

    @Override
    public void print(String name) {
        reloadBeanService.print(name);
    }

}
