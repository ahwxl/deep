package com.bplow.deep.base.redis;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RedisManagerFactoryBean implements FactoryBean<ShardedJedisPool>, InitializingBean,
                                    DisposableBean {

    private ShardedJedisPool shardedJedisPool;

    private String           redisServers;

    public void setRedisServers(String redisServers) {
        this.redisServers = redisServers;
    }

    @Override
    public void destroy() throws Exception {
        shardedJedisPool.destroy();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(5);
        poolConfig.setMaxWaitMillis(10000);
        poolConfig.setLifo(true);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        List<JedisShardInfo> shards = objectMapper.readValue(redisServers,
            new JedisShardServerReference<List<JedisShardServer>>());

        shardedJedisPool = new ShardedJedisPool(poolConfig, shards);
    }

    @Override
    public ShardedJedisPool getObject() throws Exception {

        return this.shardedJedisPool;
    }

    @Override
    public Class<?> getObjectType() {
        return (this.shardedJedisPool != null ? this.shardedJedisPool.getClass()
            : ShardedJedisPool.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    class JedisShardServerReference<T> extends TypeReference<List<JedisShardServer>> {

        public JedisShardServerReference() {
            super();
        }
    }

    static class  JedisShardServer extends JedisShardInfo {

        public JedisShardServer() {
            super("");
        }

        public JedisShardServer(String host) {
            super(host);
        }

    }

}
