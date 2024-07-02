package com.lecture7.assignment2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.lecture7.assignment2.service.EmailService;
import com.lecture7.assignment2.service.EmailServiceImpl;

@Configuration
@ComponentScan(basePackages = "com.lecture7.assignment2")
public class AppConfig {

    @Bean
    public EmailService emailService() {
        return new EmailServiceImpl();
    }

}
