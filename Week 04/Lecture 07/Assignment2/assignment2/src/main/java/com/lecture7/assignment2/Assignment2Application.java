package com.lecture7.assignment2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.lecture7.assignment2.entity.Email;
import com.lecture7.assignment2.entity.Employee;
import com.lecture7.assignment2.service.EmployeeServiceConstructor;
import com.lecture7.assignment2.service.EmployeeServiceField;
import com.lecture7.assignment2.service.EmployeeServiceSetter;

@SpringBootApplication
public class Assignment2Application {

	public static void main(String[] args) {
		SpringApplication.run(Assignment2Application.class, args);

		ApplicationContext context = SpringApplication.run(Assignment2Application.class, args);
		var employeeServiceConstructor = context.getBean(EmployeeServiceConstructor.class);
		var employeeServiceField = context.getBean(EmployeeServiceField.class);
		var employeeServiceSetter = context.getBean(EmployeeServiceSetter.class);
	
		var e1 = new Employee("MNG_01", "Luke", 40);
		var e2 = new Employee("DEV_01", "Nadia", 29);

		var email = new Email(e1, e2, "Working Report", "Keep up the good work!");

		employeeServiceField.notifyEmpoyee(email);
		employeeServiceConstructor.notifyEmployee(email);
		employeeServiceSetter.notifyEmployee(email);
	
	}
}
