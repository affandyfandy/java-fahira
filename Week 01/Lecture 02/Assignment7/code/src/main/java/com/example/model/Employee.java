package com.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Employee {
    private String id;
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private String department;

    public Employee(String id, String name, LocalDate dob, String address, String department){
        this.id = id;
        this.name = name;
        this.dateOfBirth = dob;
        this.address = address;
        this.department = department;
    }

    public String print(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        return id + "," + name + "," + dateOfBirth.format(formatter) + "," + address + "," + department;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getAddress(){
        return this.address;
    }

    public LocalDate getDateOfBirth(){
        return this.dateOfBirth;
    }

    public String getDepartment(){
        return this.department;
    }
}
