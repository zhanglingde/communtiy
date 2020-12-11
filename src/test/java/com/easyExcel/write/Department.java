package com.easyExcel.write;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

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


}
