package com.ling.other.modules.excel.easyexcel.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 导入Excel数据DTO
 * @author zhangling
 * @since 2020/8/31 10:22
 */
@Data
@ToString
public class ExcelExportLineDTO {

    @ExcelProperty("Id")
    @ApiModelProperty("主键id")
    private Integer poLineId;

    @ExcelIgnore
    @ApiModelProperty("关联采购单头表")
    private Integer poHeaderId;

    @ExcelIgnore
    @ApiModelProperty("货品id，关联货品表")
    private Integer commodityId;

    @ExcelProperty("货品编码")
    @ApiModelProperty("货品编号")
    private Integer commodityNo;

    @ExcelProperty("条形码")
    @ApiModelProperty("条形码")
    private String barcode;

    @ExcelProperty("商品名称")
    @ApiModelProperty("货品名称")
    private String commodityName;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ExcelProperty("采购数量")
    @ApiModelProperty("下单数量")
    private Integer orderQuantity;

    @ExcelProperty("预计可发货数量")
    @ApiModelProperty("确认数量")
    private Integer confirmQuantity;

    @ExcelProperty("实际发货数量")
    @ApiModelProperty("发货数量")
    private Integer deliveryQuantity;
}
