package com.lecture7.assignment3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.lecture7.assignment3.entity.Email;
import com.lecture7.assignment3.entity.Employee;
import com.lecture7.assignment3.service.EmployeeServiceRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    
    private final EmployeeServiceRequest employeeServiceRequest;

    @Autowired
    public EmployeeController(EmployeeServiceRequest employeeService) {
        this.employeeServiceRequest = employeeService;
    }

    @GetMapping("/notify-email")
    public String notifyEmployee() {
        var e1 = new Employee("MNG_01", "Luke", 40);
		var e2 = new Employee("DEV_01", "Nadia", 29);
		var email = new Email(e1,e2,"Working Report","Keep up the good work!");
        employeeServiceRequest.notifyEmployee(email);
        return "Email notification sent!";
    }
}
