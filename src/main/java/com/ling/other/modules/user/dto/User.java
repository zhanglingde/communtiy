package com.ling.other.modules.user.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhangling
 * @since 2020/8/31 13:28
 */
@Data
@Builder
@ToString
public class User {

    @ExcelProperty("Id")
    private Integer id;

    @ExcelProperty("姓名")
    private String username;

    @ExcelProperty("性别")
    private String gender;

    @ExcelProperty("年龄")
    private Integer age;
}
