package com.ling.other.modules.excel.easyexcel.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 *
 * easyExcel导出合并订单
 *
 * @author zhangling 2020-10-14 10:12:20
 */
@Data
@ColumnWidth(15)
@ApiModel("包裹货品导出VO")
public class EasyExcelExportPackageVO implements Serializable {

    private static final long serialVersionUID = -94063230383429083L;

    @ExcelIgnore
    @ApiModelProperty("主键id")
    private Integer packageId;

    //@ContentLoopMerge(eachRow = 2)
    @ColumnWidth(25)
    @ExcelProperty("订单包裹号")
    @ApiModelProperty("包裹单号")
    private String packageNum;

    @ExcelProperty("物流单号")
    @ApiModelProperty("物流单号")
    private String logisticsNum;

    @ExcelProperty("货品Id")
    @ApiModelProperty("货品编号")
    private String commodityNo;

    @ExcelProperty("货品名称")
    @ApiModelProperty("货品名称")
    private String commodityName;

    @ExcelProperty("条形码")
    @ApiModelProperty("条形码")
    private String barcode;

    @ExcelProperty("采购价")
    @ApiModelProperty("采购价")
    private BigDecimal costPrice;

    @ExcelProperty("货品数量")
    @ApiModelProperty("数量")
    private Integer quantity;

    @ExcelProperty("货品金额")
    @ApiModelProperty("货品行金额")
    private BigDecimal lineAmount;

    @ExcelProperty("收件人")
    @ApiModelProperty("收货联系人")
    private String receiptName;

    @ExcelProperty("收件电话")
    @ApiModelProperty("收货联系电话")
    private String receiptTelNum;

    @ColumnWidth(35)
    @ExcelProperty("收件地址")
    @ApiModelProperty("收货地址")
    private String receiptAddress;

    @ExcelProperty("快递公司")
    @ApiModelProperty("快递公司")
    private String courierCompany;

    @ExcelIgnore
    @ApiModelProperty("备注")
    private String remark;

    @ColumnWidth(20)
    @ExcelProperty("推单时间")
    @ApiModelProperty("流转时间")
    private Date transferTime;

    @ColumnWidth(20)
    @ExcelProperty("收货时间")
    @ApiModelProperty("收货时间")
    private Date receiptTime;

    @ExcelIgnore
    @ApiModelProperty("状态")
    private String status;

    @ExcelProperty("状态")
    private String statusMeaning;

    @ExcelIgnore
    @ApiModelProperty("发货时间")
    private Date deliveryTime;

    @ExcelIgnore
    @ApiModelProperty("更新时间")
    private Date updateTime;

}