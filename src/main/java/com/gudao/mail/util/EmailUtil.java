package com.gudao.mail.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

/**
 * 邮件工具类
 */
@Component
public class EmailUtil {

    @Value("${email.mail_from}")
    private String mailFrom;

    @Value("${email.password_mail_from}")
    private String passwordMailFrom;

    @Value("${email.mail_to}")
    private String mailTo;

    @Value("${email.mail_host}")
    private String mailHost;

    @Value("${email.mail_port}")
    private String mailPort;

    @Value("${email.copy_to}")
    private String copyMail;

    @Value("${email.works_src}")
    private String worksSrc;

    private static final String MAIL_PROTOCOL = "mail.transport.protocol";

    private static final String SMTP = "smtp";

    private static final String MAIL_HOST = "mail.smtp.host";

    private static final String MAIL_AUTH = "mail.smtp.auth";

    private static final String MAIL_AUTH_TRUE = "true";

    private static final String MAIL_PORT = "mail.smtp.port";

    private static final String MAIL_SOCKET_FACTORY = "mail.smtp.socketFactory.class";

    private static final String MAIL_SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    private static final String MAIL_SMTP_FACTORY_PORT = "mail.smtp.socketFactory.port";

    /**
     * 发送带附件的邮件
     *
     * @param mailTittle    邮件的标题
     * @param mailText      邮件的文本内容
     * @param attachmentSrc 附件地址
     * @throws Exception
     */
    public void sendMailWithAttachments(String mailTittle, String mailText, String attachmentSrc) throws MessagingException, IOException {
        Properties prop = new Properties();
        prop.setProperty(MAIL_PROTOCOL, SMTP);
        prop.setProperty(MAIL_HOST, mailHost);
        prop.setProperty(MAIL_PORT, mailPort);
        prop.setProperty(MAIL_AUTH, MAIL_AUTH_TRUE);
        prop.setProperty(MAIL_SOCKET_FACTORY, MAIL_SSL_FACTORY);
        prop.setProperty(MAIL_SMTP_FACTORY_PORT, mailPort);

        // 使用JavaMail发送邮件的5个步骤
        // 1、创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        // 2、通过session得到transport对象
        Transport ts = session.getTransport();
        // 3、连上邮件服务器，需要发件人提供邮箱的用户名和密码进行验证
        ts.connect(mailHost, mailFrom, passwordMailFrom);
        // 4、创建邮件
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息
        message.setFrom(new InternetAddress(mailFrom, "测试者"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        // 设置抄送
        if (!StringUtils.isEmpty(copyMail)) {
            InternetAddress[] addresses = InternetAddress.parse(copyMail);
            message.setRecipients(Message.RecipientType.CC, addresses);
        }
        // 邮件标题
        message.setSubject(mailTittle);
        // 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(mailText, "text/html;charset=UTF-8");
        // 创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        mp.setSubType("mixed");
        // 创建邮件附件
        if (!StringUtils.isEmpty(attachmentSrc)) {
            MimeBodyPart attach = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource(attachmentSrc));
            attach.setDataHandler(dh);
            attach.setFileName(MimeUtility.encodeText(dh.getName()));
            mp.addBodyPart(attach);
        }
        message.setContent(mp);
        message.saveChanges();
        // 将创建的Email写入到服务器存储
        message.writeTo(new FileOutputStream(worksSrc + LocalDate.now().minusDays(1) + "ImageMail" + System.currentTimeMillis() + ".eml"));
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }
}
