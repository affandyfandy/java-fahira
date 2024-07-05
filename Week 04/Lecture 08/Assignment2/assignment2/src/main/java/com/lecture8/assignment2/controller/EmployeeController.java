package com.lecture8.assignment2.controller;

import org.springframework.web.bind.annotation.RestController;

import com.lecture8.assignment2.entity.Employee;
import com.lecture8.assignment2.service.EmployeeService;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        var listEmployee = employeeService.findAll();
        if (listEmployee.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listEmployee);
    }
    
    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        var findEmployee = employeeService.findEmployeeById(employee.getId());
        if (findEmployee != null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(employeeService.save(employee));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        var employee = employeeService.findEmployeeById(id);
        if (employee != null){
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id){
        var employee = employeeService.findEmployeeById(id);
        if (employee != null){
            return ResponseEntity.ok(employeeService.delete(id));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        var findEmployee = employeeService.findEmployeeById(id);
        if (findEmployee != null){
            employee.setId(findEmployee.getId());
            return ResponseEntity.ok(employeeService.update(employee));
        }
        return ResponseEntity.badRequest().build();
    }
}
