package com.reflect;

import java.lang.annotation.*;

/**
 * @author zhangling
 * @since 2020/9/7 13:27
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {
    String value();
}
