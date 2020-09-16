package com.ling.other.modules.lov.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * Lov Value VO
 *
 * @author zhangling
 * @since 2020-07-28 15:37:19
 */
@Data
@ApiModel("Lov Value VO")
public class LovValueVO implements Serializable {

    private static final long serialVersionUID = -25852273502499429L;

    @ApiModelProperty("主键id")
    private Integer lovValueId;

    @ApiModelProperty("值集值")
    private String value;

    @ApiModelProperty("含义")
    private String meaning;

    @ApiModelProperty("描述")
    private String description;

}