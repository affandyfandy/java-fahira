package com.lecture8.assignment2.entity;

import java.sql.Date;
import javax.persistence.*;

@Entity
@Table(name="employee")
public class Employee {

    private String id;
    private String name;
    private Date dob;
    private String address;
    private String department;

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
    
    public Date getDob(){
        return this.dob;
    }

    public String getAddress(){
        return this.address;
    }

    public String getDepartment(){
        return this.department;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public void setDob(Date dob){
        this.dob = dob;
    }
}
