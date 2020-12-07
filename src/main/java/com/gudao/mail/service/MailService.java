package com.gudao.mail.service;

/**
 * 邮件发送服务
 **/
public interface MailService {
    /**
     * 发送普通邮件
     *
     * @param to      接收人邮箱
     * @param subject 标题
     * @param content 内容
     * @param copyTo  抄送邮箱
     */
    void sendSimpleMail(String to, String subject, String content, String... copyTo);

    /**
     * 发送HTML邮件
     *
     * @param fromName 发件人名字，可为空
     * @param to       接收人邮箱
     * @param subject  标题
     * @param content  正文HTML
     * @param copyTo   抄送邮箱
     */
    void sendHtmlMail(String fromName, String to, String subject, String content, String... copyTo);

    /**
     * 发送带附件的邮件
     *
     * @param fromName 发件人名字，可为空
     * @param to       接收人邮箱
     * @param subject  标题
     * @param content  内容
     * @param filePath 附件本地路径
     * @param copyTo   抄送邮箱
     */
    void sendAttachmentMail(String fromName, String to, String subject, String content, String filePath, String... copyTo);

    /**
     * 发送带图片的邮件
     *
     * @param fromName 发件人名字，可为空
     * @param to       接收人邮箱
     * @param subject  标题
     * @param content  正文HTML内容
     * @param rscPath  图片本地路径
     * @param rscId    图片ID
     * @param copyTo   抄送邮箱
     */
    void sendInlineResourceMail(String fromName, String to, String subject, String content, String rscPath, String rscId, String... copyTo);
}
