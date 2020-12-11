package com.easyExcel.write;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author zhangling
 * @since 2020/12/11 15:57
 */
@Data
@Builder
public class Department {

    /**
     * 部门Id
     */
    @ExcelProperty("部门Id")
    private Integer departmentId;

    @ExcelProperty("部门名称")
    private String departmentName;

    /**
     * 部门下的员工
     */
    private List<Employee> employees;


}
