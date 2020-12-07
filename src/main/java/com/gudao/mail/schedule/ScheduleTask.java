package com.gudao.mail.schedule;

import com.gudao.mail.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 定时任务
 */
@Component
@Configuration
@EnableScheduling
@Slf4j
public class ScheduleTask {

    @Autowired
    private EmailUtil emailUtil;

    /**
     * 定时发送邮件
     */
    @Async
    @Scheduled(cron = "0 * 2 * * ?")
    void updateVisitCardCommunication() throws IOException, MessagingException {
        emailUtil.sendMailWithAttachments("JavaMail邮件主题", "邮件正文", null);
        log.info(LocalDateTime.now() + "发了邮件");
    }
}
