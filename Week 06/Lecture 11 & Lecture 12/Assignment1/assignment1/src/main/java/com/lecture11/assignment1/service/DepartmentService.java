package com.lecture11.assignment1.service;

import java.util.List;

import com.lecture11.assignment1.model.Department;
import com.lecture11.assignment1.model.DepartmentEmployee;
import com.lecture11.assignment1.model.DepartmentManager;

public interface DepartmentService {
    
    List<Department> findAll();
    Department add(Department dept);
    Department findById(String id);
    Department update(Department dept, String id);
    void delete(String id);
    Department addEmployee(String id, DepartmentEmployee deptEmp);
    Department addManager(String id, DepartmentManager deptManager);
}
