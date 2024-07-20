package com.lecture7.assignment2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lecture7.assignment2.entity.Email;
import com.lecture7.assignment2.entity.Employee;

@Service
public class EmployeeServiceField {
    
    @Autowired
    EmailService emailService;

    public Email writeEmail(Employee sender, Employee receiver, String subject, String body){
        var email = new Email(sender, receiver, subject, body);
        return email;
    }

    public void notifyEmpoyee(Email email){
        emailService.send(email);
        System.out.println("Notify email service done using field injection");
    }

}
