package com.ling.other.modules.mail.controller;

import com.ling.other.modules.mail.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import java.util.Properties;

/**
 * @author zhangling 2021/2/24 10:37
 */
@RestController
@Api(tags = "邮件服务")
@RequestMapping("/mail")
public class MailController {
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    MailService mailService;

    @ApiOperation("发送纯文本文件")
    @GetMapping("/send")
    public void send() {
        mailService.send();
    }

    @ApiOperation("发送样式邮件")
    @GetMapping("/send-style-mail")
    public void sendStyleMail(){
        mailService.sendStyleMail();
    }

    @ApiOperation("读取邮箱邮件")
    @GetMapping("/readMail")
    public void readMail() {
        String username = "xpg.hangzhou@xpandago.com";
        String password = "Xpanda202014";
        String host = "outlook.office365.com";
        String mailStoreType = "pop3";
        String popPort = "995";
        Store store = null;

        Properties properties = new Properties();
        properties.put("mail.store.protocol", mailStoreType);
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", popPort);
        properties.put("mail.pop3.starttls.enable", "true");

        try {
            Session emailSession = Session.getDefaultInstance(properties, null);
            //打开调式模式，可看到邮件发送过程中各步骤的详细信息
            //emailSession.setDebug(true);
            System.out.println("==========");
            store = emailSession.getStore("pop3s");
            store.connect(host, username, password);
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            Message[] messages = emailFolder.getMessages();

            for (Message message : messages) {
                System.out.println(message.getSubject());
            }
            emailFolder.close(true);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != store) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @GetMapping("/test4")
    public void test4() {
        String username = "1492174237@qq.com";
        String password = "tljsycznrcfsjbeh";
        String host = "imap.qq.com";
        String mailStoreType = "imap";
        String popPort = "993";
        Store store = null;

        Properties properties = new Properties();
        properties.put("mail.store.protocol", mailStoreType);
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", popPort);
        properties.put("mail.imap.ssl.enable", "true");

        try {
            Session emailSession = Session.getDefaultInstance(properties, null);
            //emailSession.setDebug(true);
            System.out.println("==========");
            store = emailSession.getStore("imap");
            store.connect(host, username, password);
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            Message[] messages = emailFolder.getMessages();
            for (Message message : messages) {
                System.out.println(message.getSubject());
            }
            emailFolder.close(true);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != store) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @GetMapping("/test3")
    public void test3() {
        String username = "xpg.hangzhou@xpandago.com";
        String password = "Xpanda202014";
        String host = "smtp.office365.com";
        String mailStoreType = "pop3";
        String port = "587";
        Store store = null;

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        //properties.put("mail.smtp.socketFactory.fallback", true);

        try {
            Session emailSession = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("xpg.hangzhou@xpandago.com","Xpanda202014");
                }
            });
            emailSession.setDebug(true);
            System.out.println("==========");
            MimeMessage message = new MimeMessage(emailSession);
            message.addHeader("测试","测试邮件");
            message.setFrom(new InternetAddress("xpg.hangzhou@xpandago.com"));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress("390597591@qq.com"));
            message.setSubject("测试邮件");
            message.setText("<font color='red'>这是一封测试邮件</font>");
            Transport.send(message);
            System.out.println("===========发送成功===========");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
