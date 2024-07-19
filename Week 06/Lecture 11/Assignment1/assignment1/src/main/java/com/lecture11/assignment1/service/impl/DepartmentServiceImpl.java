package com.lecture11.assignment1.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lecture11.assignment1.model.Department;
import com.lecture11.assignment1.repository.DepartmentRepository;
import com.lecture11.assignment1.service.DepartmentService;

import jakarta.transaction.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    @Transactional
    public Department add(Department dept) {
        departmentRepository.save(dept);
        return dept; 
    }

    @Override
    @Transactional
    public Department findById(String id) {
        return departmentRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Department update(Department dept, String id) {
        var findDept = departmentRepository.findById(id);
        if (findDept.isPresent()){
            Department department = findDept.get();
            department.setDeptName(dept.getDeptName());
            department.setDeptNo(dept.getDeptNo());
            departmentRepository.save(department);
        }
        return findDept.get();
    }

    @Override
    public void delete(String id) {
        departmentRepository.delete(departmentRepository.findById(id).get());
    }
    
}
