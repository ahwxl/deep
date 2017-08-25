package com.bplow.deep.base;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisClientTest {
    
    @Test
    public void cashceTest() throws IOException{
        InputStream in = this.getClass().getResourceAsStream("/log4j.xml");
        String xml = IOUtils.toString(in);
        
        for(int i =0 ;i < 100000;i++){
            Jedis jedis = new Jedis("192.168.89.80");
            jedis.get("foo"+i);
        }
        
        
    }

}
