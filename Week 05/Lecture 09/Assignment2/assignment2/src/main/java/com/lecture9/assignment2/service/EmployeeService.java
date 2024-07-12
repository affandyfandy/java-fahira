package com.lecture9.assignment2.service;

import java.util.List;

import com.lecture9.assignment2.model.Employee;

public interface EmployeeService {

    void save(Employee e);
    List<Employee> findAll();
    Employee findById(String id);
    void deleteById(String id);
    void saveAll(List<Employee> employees);
}
