package com.bplow.deep.base.redis;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisCache implements Cache {

    private Logger                  logger = LoggerFactory.getLogger(getClass());

    private RedisSerializer<Object> redisSerializer;

    private ShardedJedisPool        shardedJedisPool;

    private String                  name;

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
        long startTime = System.currentTimeMillis();
        ShardedJedis shardedJedis = null;
        Object element = null;
        try {
            shardedJedis = this.shardedJedisPool.getResource();
            byte[] value = shardedJedis.get((this.name + key).getBytes());

            if (null == value) {
                return null;
            }

            element = redisSerializer.deserialize(value);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }

        //shardedJedis.close();
        logger.debug("获取缓存数据耗时:{}", System.currentTimeMillis() - startTime);
        return (element != null ? new SimpleValueWrapper(element) : null);
    }

    @Override
    public void put(Object key, Object value) {
        logger.info("开始获取缓存资源》》》");
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = this.shardedJedisPool.getResource();
            logger.info("<<<等到资源");
            shardedJedis.set((this.name + key).getBytes(), redisSerializer.serialize(value));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shardedJedis.close();
        }

        //shardedJedis.close();
    }

    @Override
    public void evict(Object key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        jedis.del(((String) key).getBytes());
        jedis.close();
    }

    @Override
    public void clear() {
        String key = this.name + "*";
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = this.shardedJedisPool.getResource();
            Jedis jedis;
            String script = "table.foreach(redis.call('KEYS', KEYS[1]), function(index, value) redis.call('DEL',value); end);";
            for (Iterator<Jedis> i$ = shardedJedis.getAllShards().iterator(); i$.hasNext(); jedis.eval(
                script, Arrays.asList(new String[] { key }), Collections.EMPTY_LIST)) {
                jedis = i$.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(null != shardedJedis){
                shardedJedis.close();
            }
        }
        
        

    }

}
