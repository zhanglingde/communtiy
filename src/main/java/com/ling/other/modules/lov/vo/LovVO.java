package com.ling.other.modules.lov.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.util.List;


/**
 * 返回值VO
 *
 * @author zhangling
 * @since 2020-09-14 13:31:44
 */
@Data
@Builder
@ApiModel("lov表VO")
public class LovVO implements Serializable {

    private static final long serialVersionUID = -89999311604615802L;


    @ApiModelProperty("主键id")
    private Integer lovId;

    @ApiModelProperty("LOV代码")
    private String lovCode;

    @ApiModelProperty("值集名称")
    private String lovName;

    @ApiModelProperty("描述")
    private String description;

    private List<LovValueVO> lovValueList;

    public LovVO() {
    }

    public LovVO(Integer lovId, String lovCode, String lovName, String description, List<LovValueVO> lovValueList) {
        this.lovId = lovId;
        this.lovCode = lovCode;
        this.lovName = lovName;
        this.description = description;
        this.lovValueList = lovValueList;
    }
}