package com.easyExcel.write;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangling
 * @since 2020/12/11 15:20
 */
@Data
@Builder
public class Employee {

    /**
     * 员工id
     */
    @ExcelProperty("员工Id")
    private Integer employeeId;
    /**
     * 员工姓名
     */
    @ExcelProperty("员工姓名")
    private String employeeName;
    /**
     * 员工生日
     */
    @ColumnWidth(30)
    @ExcelProperty("员工生日")
    private Date birthday;

    @ExcelProperty("部门Id")
    private Integer departmentId;

    @ExcelProperty("部门名称")
    private String departmentName;

}
