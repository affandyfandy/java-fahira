package com.lecture7.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeApplication.class, args);
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		Employee employee = context.getBean(Employee.class);
        // Employee employee = context.getBean("employee", Employee.class);
		employee.working();
	}

}
