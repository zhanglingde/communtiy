package com.ling.other.common.utils.dto;

import lombok.Data;

/**
 * @author zhangling 2021/3/9 9:47
 */
@Data
public class MailDTO {

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件正文
     */
    private String content;

    /**
     * 收件人
     */
    private String receipt;
}
