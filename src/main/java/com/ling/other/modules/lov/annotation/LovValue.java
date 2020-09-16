package com.ling.other.modules.lov.annotation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangling
 * @since 2020/7/24 17:51
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LovValue {

    @AliasFor("lovCode")
    String value() default StringUtils.EMPTY;

    @AliasFor("value")
    String lovCode() default StringUtils.EMPTY;

    /**
     * 处理后的含义字段放在哪个字段中<br/>
     * 不设置则进行默认映射,将注解所在字段名末尾的Code(如果有)替换为Meaning,例:<br/>
     * <ul>
     * <li>statusCode -> statusMeaning</li>
     * <li>processStatus -> processStatusMeaning</li>
     * <li>codeTypeCode -> codeTypeMeaning</li>
     * </ul>
     */
    String meaningField() default StringUtils.EMPTY;

    /**
     * @return 如果处理失败,含义字段默认设置的值,默认为原value
     */
    String defaultMeaning() default StringUtils.EMPTY;
}
