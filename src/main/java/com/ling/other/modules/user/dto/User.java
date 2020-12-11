package com.ling.other.modules.user.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

/**
 * @author zhangling
 * @since 2020/8/31 13:28
 */
@Data
@AllArgsConstructor
@Builder
@ToString
public class User {

    public User() {
    }

    @ExcelProperty("Id")
    private Integer id;

    @ExcelProperty("姓名")
    private String username;

    @ExcelProperty("性别")
    private String gender;

    @ExcelProperty("年龄")
    private Integer age;


    //@Override
    //public boolean equals(Object o) {
    //
    //    User user = (User) o;
    //    if(this.username.equals(user.getUsername())){
    //        return true;
    //    }
    //    return false;
    //}


}
