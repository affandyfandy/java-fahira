package com.lecture8.assignment2.entity;

import java.sql.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee")
public class Employee {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "address")
    private String address;

    @Column(name = "department")
    private String department;
}
