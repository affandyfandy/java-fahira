package com.lacture9.assignment3.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="employee")
@Getter
@Setter
public class Employee {

	@Id
	@Column(name="id")
	private String id;

	@Column(name="name")
	private String name;

	@Column(name="dob")
	private LocalDate dob;

	@Column(name="address")
	private String address;

	@Column(name="department")
	private String department;

	@Column(name="salary")
	private int salary;

}