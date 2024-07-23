package com.lecture7.code.entity;

import com.lecture7.code.EmployeeWork;

public class Employee {

    private String id;
    private String name;
    private int age;
    private EmployeeWork employeeWork;

    public Employee() {}

    public Employee(String id, String name, int age, EmployeeWork employeeWork){
        this.id = id;
        this.name = name;
        this.age = age;
        this.employeeWork = employeeWork;
    }

    public void working(){
        System.out.println("My Name is: " + name);
        employeeWork.work();
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setEmployeeWork(EmployeeWork employeeWork){
        this.employeeWork = employeeWork;
    }

    public String getName(){
        return name;
    }

    public EmployeeWork getEmployeeWork(){
        return employeeWork;
    }

    public String getId(){
        return id;
    }

    public int getAge(){
        return age;
    }
}
