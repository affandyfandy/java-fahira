package com.lecture11.assignment1.service;

import com.lecture11.assignment1.model.Employee;
import com.lecture11.assignment1.model.Salary;
import com.lecture11.assignment1.model.Title;
import com.lecture11.assignment1.specification.SearchCriteria;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    Page<Employee> findAll(Pageable pageable);
    Employee findById(int id);
    Employee add(Employee employee);
    Employee update(Employee employee, int id);
    void delete(Employee employee);
    Employee updateSalary(Employee employee, Salary salary);
    Employee updateTitle(Employee employee, Title title);
    Page<Employee> searchEmployees(List<SearchCriteria> params, Pageable pageable);
}