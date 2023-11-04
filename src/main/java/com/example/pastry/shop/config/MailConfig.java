package com.example.pastry.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {


    @Bean
    public JavaMailSender javaMailSender(@Value("${spring.mail.host}") String mailHost,
                                         @Value("${spring.mail.port}") Integer mailPort,
                                         @Value("${spring.mail.username}") String mailUsername,
                                         @Value("${spring.mail.password}") String mailPassword
    ) {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(mailPort);
        javaMailSender.setUsername(mailUsername);
        javaMailSender.setPassword(mailPassword);
        javaMailSender.setJavaMailProperties(mailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");
        return javaMailSender;
    }

    private Properties mailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.ssl.trust", "*");
        return properties;

    }
}
