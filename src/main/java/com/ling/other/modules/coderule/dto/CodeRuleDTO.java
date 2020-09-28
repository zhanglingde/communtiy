package com.ling.other.modules.coderule.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import lombok.*;

import javax.persistence.*;


/**
 * 数据传输模型
 *
 * @author zhangling
 * @since 2020-09-28 14:32:14
 */
@Data
@Builder
@AllArgsConstructor
@ApiModel("编码规则头DTO")
@Table(name = "au_code_rule")
public class CodeRuleDTO implements Serializable {

    private static final long serialVersionUID = -23817302506773619L;

    public CodeRuleDTO() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;

    @ApiModelProperty("编码规则code")
    private String ruleCode;

    @ApiModelProperty("编码规则名")
    private String ruleName;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("创建日期 ")
    private Date createTime;

    @ApiModelProperty("最后更新日期 ")
    private Date updateTime;

    @Transient
    @ApiModelProperty(value = "编码规则分配", hidden = true)
    private List<CodeRuleDistDTO> codeRuleDistDTOList;

}