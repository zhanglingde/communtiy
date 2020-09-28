package com.ling.other.modules.coderule.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 数据传输模型
 *
 * @author zhangling
 * @since 2020-09-28 14:55:06
 */
@Data
@Builder
@AllArgsConstructor
@ApiModel("编码规则分配DTO")
@Table(name = "au_code_rule_dist")
public class CodeRuleDistDTO implements Serializable {

    private static final long serialVersionUID = -23882923761521648L;

    public CodeRuleDistDTO() {
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleDistId;

    private Long ruleId;

    @ApiModelProperty("租户级下的应用层级")
    private String levelCode;

    @ApiModelProperty("应用层级值")
    private String levelValue;

    @ApiModelProperty(" 是否启用")
    private Integer enabledFlag;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("创建日期")
    private Date createTime;

    @ApiModelProperty("最后更新日期")
    private Date updateTime;

}