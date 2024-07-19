package com.lecture11.assignment1.service;

import java.util.List;
import com.lecture11.assignment1.model.Employee;

public interface EmployeeService {
    
    List<Employee> findAll();
    Employee findById(int id);
    Employee add(Employee employee);
    Employee update(Employee employee, int id);
    void delete(Employee employee);
}
