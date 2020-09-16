package com.ling.other.modules.easyexcel.vo;

import com.ling.other.common.annotation.ExcelColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 返回值VO
 *
 * @author zhangling
 * @since 2020-07-30 15:25:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("采购订单行表VO")
public class PoLineVO implements Serializable {

    private static final long serialVersionUID = -89856073428128811L;

    @ExcelColumn(value = "Id",col = 1)
    @ApiModelProperty("主键id")
    private Integer poLineId;

    @ApiModelProperty("关联采购单头表")
    private Integer poHeaderId;

    @ApiModelProperty("货品id，关联货品表")
    private Integer commodityId;

    @ExcelColumn(value = "货品编码",col = 2)
    @ApiModelProperty("货品编号")
    private Integer commodityNo;

    @ExcelColumn(value = "条形码",col = 3)
    @ApiModelProperty("条形码")
    private String barcode;

    @ExcelColumn(value = "商品名称",col = 4)
    @ApiModelProperty("货品名称")
    private String commodityName;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ExcelColumn(value = "采购数量",col = 5)
    @ApiModelProperty("下单数量")
    private Integer orderQuantity;

    @ExcelColumn(value = "预计可发货数量",col = 6)
    @ApiModelProperty("确认数量")
    private Integer confirmQuantity;

    @ApiModelProperty("发货数量")
    private Integer deliveryQuantity;

    @ApiModelProperty("正品数量")
    private Integer qualityProductQuantity;

    @ApiModelProperty("残品数量")
    private Integer defectiveProductQuantity;

    @ApiModelProperty("币种")
    private String currency;
    private String currencyMeaning;

    @ApiModelProperty("行金额")
    private BigDecimal lineAmounts;

}