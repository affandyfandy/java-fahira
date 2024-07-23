package com.lecture11.assignment1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lecture11.assignment1.model.DepartmentManager;
import com.lecture11.assignment1.repository.DepartmentManagerRepository;
import com.lecture11.assignment1.service.DepartmentManagerService;

import jakarta.transaction.Transactional;

@Service
public class DepartmentManagerServiceImpl implements DepartmentManagerService{

    @Autowired
    private DepartmentManagerRepository departmentManagerRepository;

    @Override
    @Transactional
    public void create(DepartmentManager deptManager) {
        departmentManagerRepository.save(deptManager);
    }
}
