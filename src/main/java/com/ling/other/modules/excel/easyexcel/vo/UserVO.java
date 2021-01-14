package com.ling.other.modules.excel.easyexcel.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 导出用户Excel模板
 * @author zhangling 2021/1/13 17:05
 */
@Data
@AllArgsConstructor
@Builder
@ToString
@HeadRowHeight(30)  // 标题头高度
@ContentRowHeight(20)   // 内容行高度
@ColumnWidth(15)    // 列宽度
public class UserVO {

    public UserVO() {
    }

    @ExcelProperty("Id")
    private Integer id;

    @ExcelProperty("姓名")
    private String username;

    @ExcelProperty("性别")
    private String gender;

    @ExcelProperty("年龄")
    private Integer age;

    @ColumnWidth(20)  // 单独设置列宽度
    @DateTimeFormat("yyyy-MM-dd")  // 导出导入日期格式设置
    @ExcelProperty("生日")
    private Date birth;


}
