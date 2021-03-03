package com.ling.other.modules.token.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标志性注解，校验接口幂等性，使用拦截器处理注解
 * @author zhangling 2020/9/25 14:25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIdempotent {

}
