package com.hzb.erp.adminCenter.service;

import com.hzb.erp.service.notification.EmailNoticeSender;
import com.hzb.erp.service.notification.bo.ToEmailBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Ryan 541720500@qq.com
 * description email发送器
 */
@Service
@Slf4j
public class EmailNoticeSenderImpl implements EmailNoticeSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送email
     */
    @Override
    public void send(ToEmailBO emailBO) {
        log.info("========邮件通知========");
        log.info(emailBO.toString());
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        if (emailBO.getSubject() == null) {
            message.setSubject("教务系统通知");
        } else {
            message.setSubject(emailBO.getSubject());
        }
        // 设置邮件发送者，这个跟application.yml中设置的要一致
        message.setFrom(from);
        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        // message.setTo("10*****16@qq.com","12****32*qq.com");
        message.setTo(emailBO.getTos());
        // 设置邮件抄送人，可以有多个抄送人
//        message.setCc("12****32*qq.com");
        // 设置隐秘抄送人，可以有多个
//        message.setBcc("7******9@qq.com");
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        message.setText(emailBO.getContent());
        // 发送邮件
        javaMailSender.send(message);
    }

}
