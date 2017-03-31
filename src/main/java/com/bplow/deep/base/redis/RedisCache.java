package com.bplow.deep.base.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;

import redis.clients.jedis.ShardedJedis;

public class RedisCache implements Cache{
    
    private ShardedJedis jedis;
    
    protected SerializingConverter serializingConverter;
    
    protected DeserializingConverter deserializingConverter;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object getNativeCache() {
        return this.jedis;
    }

    @Override
    public ValueWrapper get(Object key) {
        
       byte[] value = this.jedis.get(((String)key).getBytes());
       
       Object element = deserializingConverter.convert(value);
        
        return (element != null ? new SimpleValueWrapper(element) : null);
    }

    @Override
    public void put(Object key, Object value) {
        
        jedis.set(((String)key).getBytes(), serializingConverter.convert(value));
        
    }

    @Override
    public void evict(Object key) {
        jedis.del(((String)key).getBytes());
    }

    @Override
    public void clear() {
        
    }

}
