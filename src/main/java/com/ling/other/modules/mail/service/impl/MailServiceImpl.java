package com.ling.other.modules.mail.service.impl;

import com.ling.other.modules.mail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author zhangling 2021/3/8 14:36
 */
@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String from;


    @Override
    public void send() {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("通知-纯文本邮件");
        message.setText("这是一封测试邮件!");

        message.setTo("390597591@qq.com");
        message.setFrom(from);

        mailSender.send(message);
        System.out.println("===========发送成功===========");

    }

    @Override
    public void sendStyleMail() {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("通知-测试style邮件");

            helper.setText("<b style='color:red'>这是一封测试邮件，哈哈！</b>",true);

            helper.setFrom(from);
            helper.setTo("390597591@qq.com");
            mailSender.send(mimeMessage);
            System.out.println("发送样式邮件成功");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
