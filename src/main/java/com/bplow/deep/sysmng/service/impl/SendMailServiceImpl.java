package com.bplow.deep.sysmng.service.impl;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.Assert;

import com.bplow.deep.sysmng.service.SendMailService;


public class SendMailServiceImpl implements SendMailService{

    /** The default port: -1 */
    public static final int DEFAULT_PORT = -1;
    
    private Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);
    
    private Properties javaMailProperties = new Properties();

    private Session session;

    private String host;

    private int port = DEFAULT_PORT;

    private String username;

    private String password;
    
    private VelocityEngine velocityEngine;
    
    public void init(){
        
        Properties p = new Properties();
        String path = "";
        p.setProperty(Velocity.OUTPUT_ENCODING, "GBK");
        p.setProperty(Velocity.INPUT_ENCODING, "GBK");
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
  
        VelocityEngine engine = this.getVelocityEngine();
        try {  
            engine.init(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public String getEmailCnt(String templateName,Map map) {
        StringWriter sw = new StringWriter();
        
        VelocityContext context = new VelocityContext();
        
        Iterator<Map.Entry> entry = map.entrySet().iterator();
        while (entry.hasNext()) {
            Map.Entry<String,Object> pairs = entry.next();
            context.put(pairs.getKey(),pairs.getValue());
        }
        
        InputStream in = new DefaultResourceLoader().getClassLoader().getResourceAsStream(templateName);
        try {
            //engine.mergeTemplate(templateName, context, sw);
            this.velocityEngine.evaluate(context, sw, "mylag", in);
        } catch (ResourceNotFoundException e) {
            logger.error("", e);
        } catch (ParseErrorException e) {
            logger.error("", e);
        } catch (MethodInvocationException e) {
            logger.error("", e);
        } catch (Exception e) {
            logger.error("", e);
        }
        
        logger.info(sw.toString());
        
        return sw.toString();
    }
    
    @Override
    public void sendMail(SimpleMailMessage msg) {
        try {
            Message aimMsg = this.getMessage(msg);
            Transport.send(aimMsg);
        } catch (MessagingException e) {
            logger.error("发送email"+msg.getTo()[0], e);
        }
    }
    
    public Message getMessage(SimpleMailMessage msg) throws MessagingException{
        String emailList = msg.getTo()[0];
        if(StringUtils.isEmpty(emailList)){
            return null;
        }
        if(emailList.contains("，") || emailList.contains(";")){
            emailList = emailList.replace("，", ",");
            emailList = emailList.replace(";", ",");
        }
        InternetAddress[] ias = InternetAddress.parse(emailList, true);
        Message aimMsg = new MimeMessage(this.getSession());
        aimMsg.setFrom(new InternetAddress(msg.getFrom()));
        aimMsg.setRecipients(Message.RecipientType.TO,ias);  
        
        aimMsg.setSubject(msg.getSubject());
        //aimMsg.setText(msg.getText());
        aimMsg.setContent(msg.getText(), "text/html;charset = GBK");
        aimMsg.setSentDate(new Date());
        
        return aimMsg;
    }

    public synchronized void setSession(Session session) {
        Assert.notNull(session, "Session must not be null");
        this.session = session;
    }
    
    public synchronized Session getSession() {
        if (this.session == null) {
            this.session = Session.getInstance(this.javaMailProperties, new Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication(username, password);

                }
            });
        }
        return this.session;
    }
    
    public synchronized VelocityEngine getVelocityEngine(){
        if(this.velocityEngine == null){
            this.velocityEngine = new VelocityEngine();
        }
        return this.velocityEngine;
    }
    
    public void setJavaMailProperties(Properties javaMailProperties) {
        this.javaMailProperties = javaMailProperties;
        synchronized (this) {
            this.session = null;
        }
    }
    
    public Properties getJavaMailProperties() {
        return this.javaMailProperties;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

}
