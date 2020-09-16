package com.ling.other.modules.user.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.*;


/**
 * 数据传输模型
 *
 * @author zhangling
 * @since 2020-08-10 14:16:50
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("供应商用户表DTO")
public class SupplierUserDTO implements Serializable {

    private static final long serialVersionUID = -91252002860413464L;


    @ApiModelProperty("主键id")
    private Integer userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("公司id")
    private Integer companyId;

    @ApiModelProperty("token值，用户登录凭证")
    private String token;

    @ApiModelProperty("创建更新token时间")
    private String tokenTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("父id")
    private Integer parentId;

    @ApiModelProperty("启动标识，1启用，0禁用")
    private Integer enabledFlag;

}