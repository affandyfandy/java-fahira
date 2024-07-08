package com.lecture7.assignment2.service;

import org.springframework.stereotype.Service;

import com.lecture7.assignment2.entity.Email;
import com.lecture7.assignment2.entity.Employee;

@Service
public class EmployeeServiceConstructor {

    final EmailService emailService;

    public EmployeeServiceConstructor(EmailService emailService) {
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

