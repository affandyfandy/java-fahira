package com.lecture8.assignment2.service;

import java.util.List;
import com.lecture8.assignment2.entity.Employee;

public interface EmployeeService {

    Employee save(Employee employee);
    List<Employee> findAll();
    Employee findEmployeeById(String id);
    Employee update(Employee employee);
    String delete(String id);

}
