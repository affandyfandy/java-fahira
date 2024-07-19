package com.lecture11.assignment1.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;

import com.lecture11.assignment1.model.Employee;
import com.lecture11.assignment1.service.EmployeeService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> listEmployee = employeeService.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(listEmployee);
    }
    
    @PostMapping
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee){
        Employee newEmployee = employeeService.add(employee);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(newEmployee); 
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.update(employee, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id){
        Employee employee = employeeService.findById(id);
        if (employee != null){
            employeeService.delete(employee);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Delete successful!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find the employee");
    }
}
