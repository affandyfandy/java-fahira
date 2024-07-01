package com.fsoft.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsoft.assignment.model.Employee;
import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{
    List<Employee> findByDepartment(String department);
}
