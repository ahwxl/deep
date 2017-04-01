package com.bplow.deep.base.redis;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import redis.clients.jedis.ShardedJedisPool;

public class RedisCacheManager extends AbstractCacheManager{
    
    private ShardedJedisPool shardedJedisPool;
    
    private RedisSerializer<Object> redisSerializer;
    
    public void setRedisSerializer(RedisSerializer<Object> redisSerializer) {
        this.redisSerializer = redisSerializer;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        
        Collection<Cache> caches = new LinkedHashSet<Cache>(0);
        
        return caches;
    }
    
    @Override
    public Cache getCache(String name) {
        Cache cache = new RedisCache(this.shardedJedisPool,name,redisSerializer);
        addCache(cache);
        return cache;
    }
    
}
