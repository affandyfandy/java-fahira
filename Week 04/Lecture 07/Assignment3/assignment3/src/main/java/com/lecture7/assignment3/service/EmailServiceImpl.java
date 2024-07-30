package com.lecture7.assignment3.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.lecture7.assignment3.entity.Email;

@Service
@Scope("singleton")
public class EmailServiceImpl implements EmailService{

    @Override
    public void send(Email email) {
        var sender = email.getSender();
        var receiver = email.getReceiver();
        receiver.getListAddressedEmail().add(email);
        sender.getListSentEmail().add(email);
        email.setIsDelivered(true);
        System.out.printf("Email from %s to %s has been sent successfully...\n",
                    email.getSender().getName(), email.getReceiver().getName());
    }
}
