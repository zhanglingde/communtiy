package com;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhangling
 * @since 2020/10/29 13:34
 */
@Data
@ApiModel("转换后的税率")
public class ConversionRateVO {

    @ApiModelProperty("美元税率")
    private BigDecimal USD;

    @ApiModelProperty("人民币税率")
    private BigDecimal CNY;

    @ApiModelProperty("奥元税率")
    private BigDecimal AUD;
}
