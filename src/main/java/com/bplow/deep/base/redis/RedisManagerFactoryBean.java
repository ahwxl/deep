package com.bplow.deep.base.redis;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RedisManagerFactoryBean implements FactoryBean<ShardedJedisPool>, InitializingBean, DisposableBean{
    
    private ShardedJedisPool shardedJedisPool;
    
    private String redisServers;
    
    public void setRedisServers(String redisServers) {
        this.redisServers = redisServers;
    }

    @Override
    public void destroy() throws Exception {
        shardedJedisPool.destroy();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public ShardedJedisPool getObject() throws Exception {
        
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        //objectMapper.setVisibility(FIELD, Visibility.ANY);
        
        List<JedisShardInfo> shards = objectMapper.readValue(redisServers, new JedisShardServerReference<JedisShardInfo>());
        
        shardedJedisPool = new ShardedJedisPool(poolConfig,shards);
        
        return shardedJedisPool;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
    
    class JedisShardServerReference<T> extends TypeReference<JedisShardInfo>{
        
    }

}
