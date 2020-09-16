package com.ling.other.common.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

    /**
     *  Object  转化为json 数据
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    public  static  <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }
}
