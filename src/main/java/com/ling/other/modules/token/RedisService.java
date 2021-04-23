package com.ling.other.modules.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis操作
 * @author zhangling 2020/9/25 13:58
 */
@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 存到redis
     * @param key
     * @param value
     * @param expireTime 过期时间（秒）
     * @return
     */
    public boolean setEx(String key,Object value,Long expireTime){
        boolean result = false;
        try {
            ValueOperations ops = redisTemplate.opsForValue();
            ops.set(key,value);
            redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 请求成功后移除token
     * 下次请求取相同token，该请求不会通过
     * @param key
     * @return
     */
    public boolean remove(String key){
        if(exists(key)){
            return redisTemplate.delete(key);
        }
        return false;
    }
}
