package com.lacture9.assignment3.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lacture9.assignment3.model.Employee;
import com.lacture9.assignment3.repository.EmployeeRepository;
import com.lacture9.assignment3.service.EmployeeService;

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

    @Override
    public Optional<Integer> findMaxSalary() {
        return employeeRepository.findMaxSalary();
    }

    @Override
    public Optional<Integer> findMinSalary() {
        return employeeRepository.findMinSalary();
    }

    @Override
    public Double findAverageSalary() {
        return employeeRepository.findAverageSalary();
    }

    @Override
    public Optional<String> findEmployeeWithHighestSalary() {
        return employeeRepository.findEmployeeNameWithHighestSalary();
    }

    @Override
    public Optional<String> findEmployeeWithLowestSalary() {
        return employeeRepository.findEmployeeNameWithLowestSalary();
    }
    
}
