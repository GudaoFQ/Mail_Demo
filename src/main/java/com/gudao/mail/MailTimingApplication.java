package com.gudao.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MailTimingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailTimingApplication.class, args);
    }

}
