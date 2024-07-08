package com.lecture7.assignment3.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.lecture7.assignment3.entity.Email;
import com.lecture7.assignment3.entity.Employee;

@Service
@Scope("prototype")
public class EmployeeService {

    final EmailService emailService;

    public EmployeeService(EmailService emailService) {
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