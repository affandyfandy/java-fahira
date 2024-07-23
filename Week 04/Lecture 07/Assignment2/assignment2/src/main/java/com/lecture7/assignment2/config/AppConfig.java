package com.lecture7.assignment2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.lecture7.assignment2.service.EmailService;
import com.lecture7.assignment2.service.EmailServiceImpl;
import com.lecture7.assignment2.service.EmployeeServiceConstructor;
import com.lecture7.assignment2.service.EmployeeServiceField;
import com.lecture7.assignment2.service.EmployeeServiceSetter;

@Configuration
@ComponentScan(basePackages = "com.lecture7.assignment2")
public class AppConfig {

    @Bean
    public EmailService emailService() {
        return new EmailServiceImpl();
    }

    @Bean
    public EmployeeServiceConstructor employeeServiceConstructor(){
        return new EmployeeServiceConstructor(emailService());
    }

    @Bean
    public EmployeeServiceField employeeServiceField(){
        return new EmployeeServiceField();
    }

    @Bean EmployeeServiceSetter employeeServiceSetter(){
        return new EmployeeServiceSetter();
    }

}
