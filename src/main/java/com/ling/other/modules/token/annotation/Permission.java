package com.ling.other.modules.token.annotation;


import java.lang.annotation.*;

/**
 * 接口权限,使用aop拦截请求，判断接口是否有token
 *
 * @author zhangling @since 2020/7/20 14:58
 *
 * 注解用来类上怎么使用？
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    /**
     * PermissionStatus.LOGIN:登录
     * PermissionStatus.NOLOGIN 无需登录
     * @return
     */
    PermissionStatus value() default PermissionStatus.LOGIN ;

    enum PermissionStatus {

        LOGIN("login"),
        NOLOGIN("nologin");

        PermissionStatus(String string) {
        }
    }
}


