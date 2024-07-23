package com.lecture11.assignment1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lecture11.assignment1.model.DepartmentEmployee;
import com.lecture11.assignment1.repository.DepartmentEmployeeRepository;
import com.lecture11.assignment1.service.DepartmentEmployeeService;

@Service
public class DepartmentEmployeeServiceImpl implements DepartmentEmployeeService{

    @Autowired
    private DepartmentEmployeeRepository departmentEmployeeRepository;
    
    @Override
    public void create(DepartmentEmployee deptEmp) { 
        departmentEmployeeRepository.save(deptEmp);
    }
    
}
