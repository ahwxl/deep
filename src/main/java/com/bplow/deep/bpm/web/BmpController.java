package com.bplow.deep.bpm.web;

import org.apache.shiro.SecurityUtils;

public class BmpController {
    
    
    public void index(){
        
        Object user = SecurityUtils.getSubject().getPrincipal();
        
        
    }

}
