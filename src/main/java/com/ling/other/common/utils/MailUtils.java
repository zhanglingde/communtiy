package com.ling.other.common.utils;

import com.ling.other.common.exception.RrException;
import com.ling.other.common.utils.dto.MailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @author zhangling 2021/3/9 9:46
 */
@Component
public class MailUtils {
    private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

    @Autowired
    JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String from;


    /**
     * 发送普通邮件
     *
     * @param mailDTO
     */
    public void sendMail(MailDTO mailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mailDTO.getTitle());
        message.setText(mailDTO.getContent());
        message.setTo(mailDTO.getReceipt());
        message.setFrom(from);

        try {
            mailSender.send(message);
            logger.info("发送邮件成功" + message.toString());
        } catch (Exception e) {
            logger.error("发送邮件错误：" + e);
            throw new RrException("发送邮件错误：" + e.getMessage());
        }

    }

    /**
     * 发送html邮件
     *
     * @param mailDTO
     */
    public void sendHtmlMail(MailDTO mailDTO) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(mailDTO.getTitle());
            helper.setText(mailDTO.getContent(), true);
            helper.setTo(mailDTO.getReceipt());
            helper.setFrom(from);

            mailSender.send(mimeMessage);
            logger.info("发送邮件成功" + mimeMessage);
        } catch (Exception e) {
            logger.error("发送邮件错误：" + e);
            throw new RrException("发送邮件错误：" + e.getMessage());
        }
    }
}