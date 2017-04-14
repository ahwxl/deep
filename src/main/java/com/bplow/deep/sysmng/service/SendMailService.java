package com.bplow.deep.sysmng.service;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

public interface SendMailService {
    
    /**
     * 获取vm模板渲染后的内容
     * 
     * @param templateName 
     * @param map
     * @return
     */
    public String getEmailCnt(String templateName,Map map);
    
    /**
     * 发送邮件
     * 
     * @param msg
     */
    public void sendMail(SimpleMailMessage msg);
    
    public String getUsername();

}
