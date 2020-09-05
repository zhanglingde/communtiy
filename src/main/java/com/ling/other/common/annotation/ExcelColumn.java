package com.ling.other.common.annotation;

import java.lang.annotation.*;

/**
 * @Description: 导出表格实体类Excel标题位置注解
 * @ClassName: ExcelColumn
 * @Date 2020/7/22 15:48
 * @Author young
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
    /**
     * Excel标题
     *
     * @return
     * @author young
     */
    String value() default "";

    /**
     * Excel从左往右排列位置
     *
     * @return
     * @author young
     */
    int col() default 0;
}
