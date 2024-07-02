package com.lecture7.assignment2.service;

import com.lecture7.assignment2.entity.Email;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Override
    public void send(Email email) {
        var sender = email.getSender();
        var receiver = email.getReceiver();
        receiver.getListAddressedEmail().add(email);
        sender.getListSentEmail().add(email);
        email.setIsDelivered(true);
    }
}
