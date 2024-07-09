package com.lecture9.assignment2.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lecture9.assignment2.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{

    List<Employee> findAllByOrderByNameAsc();
    
}
