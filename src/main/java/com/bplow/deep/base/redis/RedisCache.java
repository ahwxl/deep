package com.bplow.deep.base.redis;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisCache implements Cache {

    private RedisSerializer<Object>  redisSerializer;

    private ShardedJedisPool shardedJedisPool;

    private String           name;

    public RedisCache(ShardedJedisPool shardedJedisPool, String name,
                      RedisSerializer<Object> redisSerializer) {
        super();
        this.shardedJedisPool = shardedJedisPool;
        this.name = name;
        this.redisSerializer = redisSerializer;
    }

    public void setRedisSerializer(RedisSerializer<Object> redisSerializer) {
        this.redisSerializer = redisSerializer;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.shardedJedisPool;
    }

    @Override
    public ValueWrapper get(Object key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        byte[] value = jedis.get((this.name+key).getBytes());

        if (null == value) {
            return null;
        }

        Object element = redisSerializer.deserialize(value);

        shardedJedisPool.returnResource(jedis);
        //jedis.close();
        return (element != null ? new SimpleValueWrapper(element) : null);
    }

    @Override
    public void put(Object key, Object value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        jedis.set((this.name + key).getBytes(), redisSerializer.serialize(value));

        jedis.close();
    }

    @Override
    public void evict(Object key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        jedis.del(((String) key).getBytes());
        jedis.close();
    }

    @Override
    public void clear() {
        String key = this.name +"*";
        ShardedJedis shardedJedis = this.shardedJedisPool.getResource();
        Jedis jedis;
        String script = "table.foreach(redis.call('KEYS', KEYS[1]), function(index, value) redis.call('DEL',value); end);";
        for(Iterator<Jedis> i$ = shardedJedis.getAllShards().iterator(); i$.hasNext(); jedis.eval(script, Arrays.asList(new String[] { key }),Collections.EMPTY_LIST)){
                   jedis = i$.next();
        }
        
        shardedJedis.close();
        
    }

}
