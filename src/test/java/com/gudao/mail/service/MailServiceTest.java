package com.gudao.mail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * 发送邮件测试类
 *
 * @author: zhagnxy
 * @create: 2020-12-01
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendSimpleMailTest() {
        mailService.sendSimpleMail("*********@qq.com", "简单邮件标题", "简单邮件内容", "wangbingshuai@zmeng123.com");
    }

    @Test
    public void sendHtmlMailTest() {
        String content = "<html>\n" +
                "<body>\n" +
                "<h3>hello world!这是一封html邮件</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("发件人", "*********@qq.com", "简单带html邮件", content);
    }

    @Test
    public void sendAttachmentMailTest() {
        String filePath = "/Users/wangbingshuai/package/java相关安装包/redis-5.0.5.tar.gz";
        mailService.sendAttachmentMail("发件人", "2562879274@qq.com", "带附件的邮件", "有附件，请查收", filePath);
    }

    @Test
    public void sendImgResourceMailTest() {
        String gudao = "gudao";
        String content = "<html><body>这是带图片的邮件：<img src=\'cid:" + gudao + "\'/></body></html>";
        String imgPath = "F:\\图片\\电脑\\wallhaven-3913ry.jpg";
        mailService.sendInlineResourceMail("图片邮件测试", "*********@qq.com", "带有图片的邮件", content, imgPath, gudao);
    }

    @Test
    public void sendTemplateMail() {
        Context context = new Context();
        context.setVariable("id", "002");
        String emailContent = templateEngine.process("emailTemplate", context);
        mailService.sendHtmlMail("发件人", "*********@qq.com", "模板邮件", emailContent);
    }
}
