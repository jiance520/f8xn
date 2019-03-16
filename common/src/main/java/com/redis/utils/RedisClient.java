package com.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisClient {

    @Autowired
    private JedisPool jedisPool;

    public void set(String key, Object value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key.getBytes(), SerializeUtil.serialize(value));
        } catch(Exception e){
           e.printStackTrace();
        }finally {
            //返还到连接池
            jedis.close();
        }
    }

    public Object get(String key){
        Object value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] temp = jedis.get(key.getBytes());
            value = SerializeUtil.unserialize(temp);
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            //返还到连接池
            jedis.close();
        }
        return value;
    }

    public long del(String key){
        long num = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            num = jedis.del(key.getBytes());
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            //返还到连接池
            jedis.close();
        }
        return num;
    }
}
