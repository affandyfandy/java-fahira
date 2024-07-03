package com.lecture7.assignment3.entity;

public class Email {
    
    private int id;
    private Employee sender;
    private Employee receiver;
    private String subject;
    private String body;
    private boolean isDelivered;
    static int counter = 1;

    public Email(Employee sender, Employee receiver, String subject, String body){
        this.id = counter;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
        counter++;
    }

    public Employee getSender(){
        return sender;
    }

    public Employee getReceiver(){
        return receiver;
    }

    public void setIsDelivered(boolean state) {
        isDelivered = state;
    }
}
