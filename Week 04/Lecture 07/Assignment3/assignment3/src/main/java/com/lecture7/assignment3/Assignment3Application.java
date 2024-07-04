package com.lecture7.assignment3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.lecture7.assignment3.entity.Email;
import com.lecture7.assignment3.entity.Employee;
import com.lecture7.assignment3.service.EmailService;
import com.lecture7.assignment3.service.EmployeeService;

@SpringBootApplication
public class Assignment3Application implements CommandLineRunner{

	private final ApplicationContext context;

	public Assignment3Application(ApplicationContext context) {
        this.context = context;
    }

	public static void main(String[] args) {
		SpringApplication.run(Assignment3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		var e1 = new Employee("MNG_01", "Luke", 40);
		var e2 = new Employee("DEV_01", "Nadia", 29);

		var email = new Email(e1,e2,"Working Report","Keep up the good work!");
     	EmailService emailService = (EmailService) context.getBean(EmailService.class);
		emailService.send(email);
		System.out.println("emailService hashCode: "+ emailService.hashCode());
		EmailService emailService2 = (EmailService) context.getBean(EmailService.class);
		emailService2.send(email);
		System.out.println("emailService hashCode: "+ emailService2.hashCode());

		System.out.println("===========================================");

     	EmployeeService employeeService = (EmployeeService) context.getBean(EmployeeService.class);
		employeeService.notifyEmployee(email);
		System.out.println("employeeService hashCode: "+ employeeService.hashCode());
		EmployeeService employeeService2 = (EmployeeService) context.getBean(EmployeeService.class);
		employeeService2.notifyEmployee(email);
		System.out.println("employeeService hashCode: "+ employeeService2.hashCode());

	}
}
