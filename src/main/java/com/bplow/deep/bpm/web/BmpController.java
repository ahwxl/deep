package com.bplow.deep.bpm.web;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BmpController {
    
    
    @RequestMapping(value="/bpm/index")
    public String index(){
        
        Object user = SecurityUtils.getSubject().getPrincipal();
        
        return "bpm/index";
    }

}
