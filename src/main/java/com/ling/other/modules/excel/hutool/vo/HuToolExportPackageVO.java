package com.ling.other.modules.excel.hutool.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.ling.other.modules.lov.annotation.LovValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 返回值VO
 *
 * @author zhangling 2020-10-14 10:12:20
 */
@Data
@ApiModel("订单包裹导出VO")
public class HuToolExportPackageVO implements Serializable {

    private static final long serialVersionUID = -94063230383429083L;


    @ApiModelProperty("包裹单号")
    private String packageNum;

    @ApiModelProperty("物流单号")
    private String logisticsNum;

    @ApiModelProperty("快递公司")
    private String courierCompany;

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

    @ApiModelProperty("收货联系人")
    private String receiptName;

    @ApiModelProperty("收货联系电话")
    private String receiptTelNum;

    @ApiModelProperty("收货地址")
    private String receiptAddress;

    @ApiModelProperty("推单时间")
    private Date transferTime;

    @ApiModelProperty("状态")
    private String status;

}