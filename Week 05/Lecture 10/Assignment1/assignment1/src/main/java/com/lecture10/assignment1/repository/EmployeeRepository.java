package com.lecture10.assignment1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lecture10.assignment1.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{
    List<Employee> findAllByDepartment(String department);
}