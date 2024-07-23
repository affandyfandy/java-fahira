package com.lecture8.assignment3.controller;

import org.springframework.web.bind.annotation.RestController;

import com.lecture8.assignment3.entity.Employee;
import com.lecture8.assignment3.service.EmployeeService;

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
@RequestMapping("/api/v2/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/db1")
    public ResponseEntity<List<Employee>> getAllEmployeesDb1() {
        var listEmployee = employeeService.findAllOnDb1();
        if (listEmployee.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listEmployee);
    }

    @GetMapping("/db2")
    public ResponseEntity<List<Employee>> getAllEmployeesDb2() {
        var listEmployee = employeeService.findAllOnDb2();
        if (listEmployee.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listEmployee);
    }
    
    @PostMapping("/db1/add")
    public ResponseEntity<Employee> addEmployeeDb1(@RequestBody Employee employee) {
        var findEmployee = employeeService.findEmployeeByIdOnDb1(employee.getId());
        if (findEmployee != null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(employeeService.saveToDb1(employee));
    }

    @PostMapping("/db2/add")
    public ResponseEntity<Employee> addEmployeeDb2(@RequestBody Employee employee) {
        var findEmployee = employeeService.findEmployeeByIdOnDb2(employee.getId());
        if (findEmployee != null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(employeeService.saveToDb2(employee));
    }
    
    @GetMapping("/db1/{id}")
    public ResponseEntity<Employee> getEmployeeByIdDb1(@PathVariable String id) {
        var employee = employeeService.findEmployeeByIdOnDb1(id);
        if (employee != null){
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/db2/{id}")
    public ResponseEntity<Employee> getEmployeeByIdDb2(@PathVariable String id) {
        var employee = employeeService.findEmployeeByIdOnDb2(id);
        if (employee != null){
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/db1/{id}/delete")
    public ResponseEntity<String> deleteEmployeeOnDb1(@PathVariable String id){
        var employee = employeeService.findEmployeeByIdOnDb1(id);
        if (employee != null){
            return ResponseEntity.ok(employeeService.deleteOnDb1(id));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/db2/{id}/delete")
    public ResponseEntity<String> deleteEmployeeOnDb2(@PathVariable String id){
        var employee = employeeService.findEmployeeByIdOnDb2(id);
        if (employee != null){
            return ResponseEntity.ok(employeeService.deleteOnDb2(id));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/db1/{id}/update")
    public ResponseEntity<Employee> updateEmployeeDb1(@PathVariable String id, @RequestBody Employee employee) {
        var findEmployee = employeeService.findEmployeeByIdOnDb1(id);
        if (findEmployee != null){
            employee.setId(findEmployee.getId());
            return ResponseEntity.ok(employeeService.updateOnDb1(employee));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/db2/{id}/update")
    public ResponseEntity<Employee> updateEmployeeDb2(@PathVariable String id, @RequestBody Employee employee) {
        var findEmployee = employeeService.findEmployeeByIdOnDb2(id);
        if (findEmployee != null){
            employee.setId(findEmployee.getId());
            return ResponseEntity.ok(employeeService.updateOnDb2(employee));
        }
        return ResponseEntity.badRequest().build();
    }

}
