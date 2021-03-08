package com.ling.other.modules.mail.service;

/**
 * @author zhangling 2021/3/8 14:36
 */
public interface MailService {

    /**
     * 发送纯文本邮件
     */
    void send();


    /**
     * 发送带样式邮件
     */
    void sendStyleMail();
}
