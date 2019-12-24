package com.ling.community.dto;

import lombok.Data;

/**
 * dto包表示是 数据传输的模型
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
