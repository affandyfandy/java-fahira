package com.lecture11.assignment1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lecture11.assignment1.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String>{
    
}
