package com.ling.other.modules.excel.hutool.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 *
 * @author zhangling 2020-10-13 14:00:23
 */
@Data
@Builder
@AllArgsConstructor
@ApiModel("订单包裹单表DTO")
public class PackageDTO implements Serializable {

    private static final long serialVersionUID = -82384986304310146L;

    public PackageDTO() {
    }

    @ApiModelProperty("主键id")
    private Integer packageId;

    @ApiModelProperty("包裹单号")
    private String packageNum;

    @ApiModelProperty("熊猫订单包裹号")
    private String xpgPackageNum;

    @ApiModelProperty("订单号")
    private String orderNum;

    @ApiModelProperty("仓库编码")
    private String warehouseNum;

    @ApiModelProperty("物流单号")
    private String logisticsNum;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("返销标识")
    private Integer reversedFlag;

    @ApiModelProperty("结算状态：0未结算，1已结算")
    private Integer settlementFlag;

    @ApiModelProperty("供应商公司id")
    private Integer companyId;

    @ApiModelProperty("供应商编号")
    private String supplierNum;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("仓库id")
    private Integer warehouseId;

    @ApiModelProperty("物流公司Id")
    private Integer courierCompanyId;

    @ApiModelProperty("快递公司")
    private String courierCompany;

    @ApiModelProperty("货品数量")
    private Integer count;

    @ApiModelProperty("包裹总金额")
    private BigDecimal amount;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("客户备注留言")
    private String userRemark;

    @ApiModelProperty("收货联系人")
    private String receiptName;

    @ApiModelProperty("收货联系电话")
    private String receiptTelNum;

    @ApiModelProperty("收货地址")
    private String receiptAddress;

    @ApiModelProperty("收货时间")
    private Date receiptTime;

    @ApiModelProperty("发货时间")
    private Date deliveryTime;

    @ApiModelProperty("推单时间")
    private Date transferTime;

    @ApiModelProperty("预售时间")
    private String estimatedDeliveryTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("修改物流原因")
    private String editReason;

    private List<PackageCommodityDTO> packageCommodityList;

}