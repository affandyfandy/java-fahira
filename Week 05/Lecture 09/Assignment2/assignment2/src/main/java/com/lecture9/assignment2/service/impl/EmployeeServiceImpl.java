package com.lecture9.assignment2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lecture9.assignment2.model.Employee;
import com.lecture9.assignment2.repository.EmployeeRepository;
import com.lecture9.assignment2.service.EmployeeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Override
    public void save(Employee e) {
        employeeRepository.save(e);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Employee findById(String theId) {
        return employeeRepository.findById(theId).orElseThrow();
    }

    @Override
    public void deleteById(String theId) {
        employeeRepository.deleteById(theId);
    }

    @Override
    public void saveAll(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }
    
}
