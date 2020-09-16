package com.ling.other.modules.lov.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用值集自动处理，将值集值转换为值集含义
 * @author zhangling
 * @since 2020/7/24 16:11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessLovValue {

    String[] targetField() default StringUtils.EMPTY;
}
