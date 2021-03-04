package com.ling.other.modules.mail.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangling 2021/2/24 10:37
 */
@RestController
@Api(tags = "邮件服务")
@RequestMapping("/mail")
public class MailController {
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    JavaMailSenderImpl mailSender;

    @ApiOperation("测试邮件")
    @GetMapping("/test")
    public void test(){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("通知-邮件测试");
        message.setText("这是一封测试邮件!");

        message.setTo("390597591@qq.com");
        message.setFrom("2015061001000219@ecjtu.edu.cn");

        String msg = null;
        try {
            mailSender.send(message);
        } catch (MailException e) {
            //e.printStackTrace();
            logger.info(String.valueOf(e.getCause()));
            //msg = String.valueOf(e.getCause());
        }
        //return msg;
    }
}
