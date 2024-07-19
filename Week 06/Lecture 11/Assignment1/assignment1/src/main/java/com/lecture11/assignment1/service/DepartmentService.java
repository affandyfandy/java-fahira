package com.lecture11.assignment1.service;

import java.util.List;

import com.lecture11.assignment1.model.Department;

public interface DepartmentService {
    
    List<Department> findAll();
    Department add(Department dept);
    Department findById(String id);
    Department update(Department dept, String id);
    void delete(String id);
}
