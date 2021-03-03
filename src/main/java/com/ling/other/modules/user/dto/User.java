package com.ling.other.modules.user.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;

/**
 * @author zhangling 2020/8/31 13:28
 */
@Data
@AllArgsConstructor
@Builder
@Accessors(chain = true)
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

    private Date birth;


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
