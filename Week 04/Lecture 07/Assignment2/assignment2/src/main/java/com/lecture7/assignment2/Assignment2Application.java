package com.lecture7.assignment2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.lecture7.assignment2.entity.Email;
import com.lecture7.assignment2.entity.Employee;
import com.lecture7.assignment2.service.EmployeeServiceConstructor;
import com.lecture7.assignment2.service.EmployeeServiceField;
import com.lecture7.assignment2.service.EmployeeServiceSetter;

@SpringBootApplication
public class Assignment2Application {

	public static void main(String[] args) {
		SpringApplication.run(Assignment2Application.class, args);
	}

	@Bean
	CommandLineRunner run(EmployeeServiceConstructor employeeServiceConstructor, 
						EmployeeServiceField employeeServiceField, 
						EmployeeServiceSetter employeeServiceSetter){
		return args -> {
			var e1 = new Employee("MNG_01", "Luke", 40);
			var e2 = new Employee("DEV_01", "Nadia", 29);

			var email = new Email(e1, e2, "Working Report", "Keep up the good work!");

			employeeServiceConstructor.notifyEmployee(email);
			employeeServiceSetter.notifyEmployee(email);
			employeeServiceField.notifyEmpoyee(email);
		};
	}
}
