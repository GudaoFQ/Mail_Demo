package com.gudao.mail.service.impl;

import com.gudao.mail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * 发送邮件服务实现
 **/
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Value("${mail.fromMail.addr}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleMail(String to, String subject, String content, String... copyTo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        if (copyTo != null && copyTo.length > 0) {
            message.setCc(copyTo);
        }
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
        log.info("简单邮件已经发送");
    }

    @Override
    public void sendHtmlMail(String fromName, String to, String subject, String content, String... copyTo) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            if (StringUtils.isEmpty(fromName)) {
                helper.setFrom(from);
            } else {
                helper.setFrom(from, fromName);
            }
            helper.setTo(to);
            if (copyTo != null && copyTo.length > 0) {
                helper.setCc(copyTo);
            }
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            log.info("html邮件发送成功");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("发送html邮件时发生异常！", e);
        }
    }

    @Override
    public void sendAttachmentMail(String fromName, String to, String subject, String content, String filePath, String... copyTo) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            if (StringUtils.isEmpty(fromName)) {
                helper.setFrom(from);
            } else {
                helper.setFrom(from, fromName);
            }
            helper.setTo(to);
            if (copyTo != null && copyTo.length > 0) {
                helper.setCc(copyTo);
            }
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            log.info("带附件的邮件已经发送");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("发送带附件的邮件时发生异常！", e);
        }
    }

    @Override
    public void sendInlineResourceMail(String fromName, String to, String subject, String content, String rscPath, String rscId, String... copyTo) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            if (StringUtils.isEmpty(fromName)) {
                helper.setFrom(from);
            } else {
                helper.setFrom(from, fromName);
            }
            helper.setTo(to);
            if (copyTo != null && copyTo.length > 0) {
                helper.setCc(copyTo);
            }
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource img = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, img);
            mailSender.send(message);
            log.info("嵌入静态资源的邮件已经发送");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("发送嵌入静态资源的文件时发生异常！", e);
        }
    }
}
