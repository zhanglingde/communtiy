package com.ling.other.modules.lov.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 值集值DTO
 * @author zhangling
 * @since 2020/7/27 10:41
 */
@Data
@ApiModel("值集值DTO")
public class LovValueDTO {

    @ApiModelProperty("主键id")
    private Integer lovValueId;

    @ApiModelProperty("关联值集头")
    private Integer lovId;

    @ApiModelProperty("值集代码")
    private String lovCode;

    @ApiModelProperty("值集值")
    private String value;

    @ApiModelProperty("含义")
    private String meaning;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("备注")
    private String remark;
}
