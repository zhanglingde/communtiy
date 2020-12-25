package com.ling.other.modules.scheduler.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 任务执行类注解
 *
 * @author zhangling
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface JobHandler {

    String value();
}