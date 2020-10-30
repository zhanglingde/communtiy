package com;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 税率返回值
 * @author zhangling
 * @since 2020/10/28 14:18
 */
@Data
public class TaxRateVO {

    @ApiModelProperty("结果")
    private String result;

    @ApiModelProperty("上次更新时间")
    private String timeLastUpdateUtc;

    @ApiModelProperty("下次更新时间")
    private String timeNextUpdateUtc;

    @ApiModelProperty("要被转换的币种")
    private String baseCode;

    @ApiModelProperty(value = "错误类型",hidden = true)
    private String errorType;

    @ApiModelProperty("查询的人民币税率")
    private ConversionRateVO conversionRates;
}
