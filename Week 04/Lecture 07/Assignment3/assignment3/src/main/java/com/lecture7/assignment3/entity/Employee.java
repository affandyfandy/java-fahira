package com.lecture7.assignment3.entity;

import java.util.ArrayList;

public class Employee {
    
    private String id;
    private String name;
    private int age;
    private ArrayList<Email> listAddressedEmail = new ArrayList<>();
    private ArrayList<Email> listSentEmail = new ArrayList<>();

    public Employee(String id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public ArrayList<Email> getListAddressedEmail(){
        return listAddressedEmail;
    }

    public ArrayList<Email> getListSentEmail(){
        return listSentEmail;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

}
