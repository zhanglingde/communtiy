package com.mail;

import cn.hutool.extra.mail.MailUtil;
import cn.hutool.extra.template.engine.thymeleaf.ThymeleafEngine;
import com.ling.other.CommunityApplication;
import com.ling.other.common.utils.MailUtils;
import com.ling.other.common.utils.dto.MailDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.swing.text.html.HTML;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author zhangling 2021/4/1 9:28
 */
@SpringBootTest(classes = CommunityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class MailTest {

    @Autowired
    MailUtils mailUtils;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Test
    public void mailSend() throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setSubject("这是测试邮件主题(thymeleaf)");
        helper.setFrom("1492174237@qq.com");
        helper.setTo("390597591@qq.com");

        LocalDate curDay = LocalDate.now();
        LocalTime curTime = LocalTime.now();
        Context context = new Context();
        context.setVariable("username", "伊利");
        context.setVariable("password", "123456");
        context.setVariable("address", "https://supplier.xpanago.com");
        context.setVariable("year",curDay.getYear());
        context.setVariable("month",curDay.getMonthValue());
        context.setVariable("day",curDay.getDayOfMonth());
        context.setVariable("hour",curTime.getHour());
        context.setVariable("minute",curTime.getMinute());
        // 传入上下文对象
        String mail = templateEngine.process("mail", context);
        System.out.println(mail);
        helper.setText(mail, true);

        javaMailSender.send(msg);
    }
}
