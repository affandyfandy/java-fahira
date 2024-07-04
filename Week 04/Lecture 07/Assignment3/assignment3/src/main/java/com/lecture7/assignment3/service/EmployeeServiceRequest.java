package com.lecture7.assignment3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.lecture7.assignment3.entity.Email;
import com.lecture7.assignment3.entity.Employee;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EmployeeServiceRequest {

    final EmailService emailService;

    @Autowired
    public EmployeeServiceRequest(EmailService emailService) {
        this.emailService = emailService;
    }

    public void notifyEmployee(Email email) {
        emailService.send(email);
        System.out.println("Notify email service done using constructor injection");
    }

    public Email writeEmail(Employee sender, Employee receiver, String subject, String body) {
        var email = new Email(sender, receiver, subject, body);
        return email;
    }

}