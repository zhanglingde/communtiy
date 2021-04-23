/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ling.other.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 */
@Component
public class RedisUtils {

    public RedisUtils() {
    }

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations ;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    public void set(String key, Object value, long expire){
        valueOperations.set(key, JsonUtils.toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : JsonUtils.fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量上次 支持模糊删除
     * @param key   goodsShare_*_2662
     */
    public void deleteBatch(String key){
        Set<String> keys = redisTemplate.keys(key);
        redisTemplate.delete(keys);
    }


    /** ------------------------list相关操作---------------------------- */

    /**
     * 将数据放入list
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean listSet(String key, Object value) {
        return listSet(key,value,DEFAULT_EXPIRE);
    }

    /**
     * 将数据放入list
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean listSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0){
                redisTemplate.expire(key, time,TimeUnit.SECONDS);
            } else {
                redisTemplate.expire(key, DEFAULT_EXPIRE,TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list数据放入list
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean listSetArrayList(String key, ArrayList<?> value) {
        return listSetArrayList(key,value,DEFAULT_EXPIRE);
    }

    /**
     * 将list数据放入list
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean listSetArrayList(String key, ArrayList<?> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0){
                redisTemplate.expire(key, time,TimeUnit.SECONDS);
            } else {
                redisTemplate.expire(key, DEFAULT_EXPIRE,TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束  0 到 -1代表所有值
     * @return
     */
    public <T> List<T> listGet(String key, long start, long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */
    public long listSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 队列
     */
    public void  convertAndSend(String channel, String message){
        redisTemplate.convertAndSend(channel,message);
    }

    /**
     * hmset操作
     * @param mapKey
     * @param param
     */
    public void hPutAll(String mapKey,Map<String,String> param){
        hashOperations.putAll(mapKey,param);
        redisTemplate.expire(mapKey,DEFAULT_EXPIRE,TimeUnit.SECONDS);
    }


    /**
     * hset操作
     * @param mapKey
     * @param key
     * @param paramMap
     */
    public void hset(String mapKey,String key, String paramMap){
        hashOperations.put(mapKey,key,paramMap);
        redisTemplate.expire(mapKey,DEFAULT_EXPIRE,TimeUnit.SECONDS);
    }

    /**
     * hget操作
     * @param mapKey
     * @param key
     * @return
     */
    public Object hget(String mapKey,Object key){
        return hashOperations.get(mapKey,key);
    }

    /**
     * hmget 操作
     * @param mapKey
     * @param params
     * @return
     */
    public List<String> hmget(String mapKey, Collection params){
        return hashOperations.multiGet(mapKey,params);
    }


    /**
     * Set 将一个或多个元素添加到给定集合里面，已经存在于集合中的元素会自动被忽略, 命令返回新添加到集合的元素数量
     * @param key
     * @param var   Set的值，可以是一个元素，也可以是多个元素的数组
     * @return 1 插入成功，0插入失败
     */
    public Long setAdd(String key,String... var){
       return setOperations.add(key,var);
    }

    /**
     * Set 查询Set
     * @param key
     * @return 返回集合中的所有元素
     */
    public Set<Object> setMembers(String key){
        return setOperations.members(key);
    }


}
