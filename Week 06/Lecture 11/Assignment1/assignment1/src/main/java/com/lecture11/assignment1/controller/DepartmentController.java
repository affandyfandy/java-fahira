package com.lecture11.assignment1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lecture11.assignment1.model.Department;
import com.lecture11.assignment1.service.DepartmentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartment() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<Department> addNewDepartment(@RequestBody Department department){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentService.add(department));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable String id, @RequestBody Department department) {
        Department updatedDept = departmentService.update(department, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedDept);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Delete successful!");
    }
    
}
