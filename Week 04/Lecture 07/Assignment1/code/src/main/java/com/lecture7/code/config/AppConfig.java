package com.lecture7.code.config;
import com.lecture7.code.EmployeeWork;
import com.lecture7.code.entity.Employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /**
     * Constructor injection
     */
    // @Bean
    // public Employee employee() {
    //     return new Employee("MNG_001", "Keiko", 35, employeeWork());
    // }

    @Bean
    public EmployeeWork employeeWork(){
        return new EmployeeWork();
    }

    /**
     * Setter injection
     */
    @Bean
    public Employee employee() {
        Employee employee = new Employee();
        employee.setName("Keiko");
        employee.setId("MNG_001");
        employee.setAge(35);
        employee.setEmployeeWork(employeeWork());
        return employee;
    }
}
