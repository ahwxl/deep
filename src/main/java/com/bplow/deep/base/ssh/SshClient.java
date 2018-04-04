package com.bplow.deep.base.ssh;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String executeCommand() {

        JSch jsch = new JSch();

        try {
            Session session = jsch.getSession("root", "192.168.89.81", 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword("test2015");

            session.connect();
            Channel channel = session.openChannel("exec");

            String command = "netstat -ant";

            ((ChannelExec) channel).setCommand(command);

            channel.setInputStream(null);
            
            ByteArrayOutputStream bytearrayOs = new ByteArrayOutputStream();
            
            ((ChannelExec) channel).setErrStream(bytearrayOs);

            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0)
                        break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0)
                        continue;
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            
            channel.disconnect();
            session.disconnect();
            
            System.out.println("=======>"+bytearrayOs.toString());
            
        } catch (JSchException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }

        return null;
    }
    
    public static void main(String[] args) {
        SshClient c = new SshClient();
        
        c.executeCommand();
        
    }

}
