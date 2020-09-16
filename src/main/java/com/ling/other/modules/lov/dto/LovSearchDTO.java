package com.ling.other.modules.lov.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * lov搜索DTO
 * @author zhangling
 * @since 2020/9/15 10:03
 */
@Data
@ApiModel("lov搜索DTO")
public class LovSearchDTO {

    @ApiModelProperty("值集编码")
    private String lovCode;

    @ApiModelProperty("值集名称")
    private String lovName;
}
