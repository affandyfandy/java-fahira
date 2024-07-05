package com.lecture8.assignment2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lecture8.assignment2.entity.Employee;
import com.lecture8.assignment2.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    
    EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> listAllEmployee(){
        List<Employee> listEmployee=employeeRepository.findAll();
        if(listEmployee.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listEmployee);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee) {
        try {
            var newEmployee = new Employee();
            newEmployee.setId(employee.getId());
            newEmployee.setName(employee.getName());
            newEmployee.setDob(employee.getDob());
            newEmployee.setAddress(employee.getAddress());
            newEmployee.setDepartment(employee.getDepartment());
            employeeRepository.save(newEmployee);
            return ResponseEntity.ok(newEmployee);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findEmployee(@PathVariable("id") String id) {
        var employee = employeeRepository.findById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") String id, @RequestBody Employee employeeForm) {
        int row = employeeRepository.update(id, employeeForm);
        if (row > 0){
            var updatedEmployee = employeeRepository.findById(id);
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id){
        int row = employeeRepository.deleteById(id);
        if (row > 0){
            return ResponseEntity.ok().body("Employee deleted successfully!");
        }
        return ResponseEntity.badRequest().body("Employee deleted failed");
    }
}
