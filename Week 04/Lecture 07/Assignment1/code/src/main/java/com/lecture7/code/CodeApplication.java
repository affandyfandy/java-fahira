package com.lecture7.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.lecture7.code.config.AppConfig;
import com.lecture7.code.entity.Employee;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeApplication.class, args);
		/**
		 * Implementation using XML declaration
		 */
		// ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

		/**
		 * Implementation using Java configuration
		 */
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Employee employee = context.getBean(Employee.class);
		employee.working();

	}

}
