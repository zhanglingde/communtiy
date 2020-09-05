package com.ling.community.dto;

import lombok.Data;

/**
 * 获取GitHub登录code的参数
 * 实体表
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
