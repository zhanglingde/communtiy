package com.ling.other.modules.lov.handle;

/**
 * 值集处理类
 * @author zhangling
 * @since 2020/7/24 16:23
 */
public interface LovValueHandle {

    /**
     * 将传入对象的指定字段进行处理
     * @param targetFields
     * @param result
     * @return
     */
    Object process(String[] targetFields,Object result) throws NoSuchFieldException, IllegalAccessException;
}
