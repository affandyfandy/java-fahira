package com.lecture11.assignment1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lecture11.assignment1.model.DepartmentEmployee;
import com.lecture11.assignment1.model.composite.DepartmentEmployeeId;

@Repository
public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployee, DepartmentEmployeeId> {
    
}