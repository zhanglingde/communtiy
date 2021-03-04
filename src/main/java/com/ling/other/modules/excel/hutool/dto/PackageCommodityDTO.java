package com.ling.other.modules.excel.hutool.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author zhangling 2020-10-13 14:09:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("包裹货品表DTO")
public class PackageCommodityDTO implements Serializable {

    private static final long serialVersionUID = -88123419901568005L;


    @ApiModelProperty("主键id")
    private Integer packageCommodityId;

    @ApiModelProperty("关联订单包裹")
    private Integer packageId;

    @ApiModelProperty("货品编号")
    private String commodityNo;

    @ApiModelProperty("货品名称")
    private String commodityName;

    @ApiModelProperty("条形码")
    private String barcode;

    @ApiModelProperty("采购价")
    private BigDecimal costPrice;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("货品行金额")
    private BigDecimal lineAmount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}