package com.lacture9.assignment3.service;

import java.util.List;
import java.util.Optional;

import com.lacture9.assignment3.model.Employee;

public interface EmployeeService {

    void save(Employee e);
    List<Employee> findAll();
    Employee findById(String id);
    void deleteById(String id);
    void saveAll(List<Employee> employees);
    Optional<Integer> findMaxSalary();
    Optional<Integer> findMinSalary();
    Double findAverageSalary();
    Optional<String> findEmployeeWithHighestSalary();
    Optional<String> findEmployeeWithLowestSalary();
}
