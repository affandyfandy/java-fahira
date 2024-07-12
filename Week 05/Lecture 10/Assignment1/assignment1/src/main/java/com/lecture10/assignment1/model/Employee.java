package com.lecture10.assignment1.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotEmpty(message = "Name can't be empty")
    @Column(name="name")
    private String name;

    @Column(name="dob")
    private LocalDate dob;

    @Column(name="address")
    private String address;

    @Column(name="department")
    private String department;

    @Email
    @NotEmpty(message = "Email can't be empty")
    @Column(name="email")
    private String email;

    @Pattern(regexp = "^\\+62[0-9]{9,12}$", message = "Phone number should be valid and start with +62")
    @NotEmpty(message = "Phone is required")
    @Column(name="phone")
    private String phone;

}