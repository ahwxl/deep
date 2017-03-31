package com.bplow.deep.base.redis;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import redis.clients.jedis.ShardedJedisPool;

public class RedisCacheManager extends AbstractCacheManager{
    
    private ShardedJedisPool shardedJedisPool;

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return null;
    }
    
    @Override
    public Cache getCache(String name) {
        
        return null;
    }
    
    

}
