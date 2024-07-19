package com.lecture11.assignment1.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lecture11.assignment1.model.Employee;
import com.lecture11.assignment1.repository.EmployeeRepository;
import com.lecture11.assignment1.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public Employee findById(int id){
        return employeeRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Employee add(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    @Transactional
    public Employee update(Employee employee, int id){
        var findEmployee = employeeRepository.findById(id);
        if (findEmployee.isPresent()){
            Employee emp = findEmployee.get();
            emp.setBirtDate(employee.getBirtDate());
            emp.setFirstName(employee.getFirstName());
            emp.setGender(employee.getGender());
            emp.setHireDate(employee.getHireDate());
            emp.setLastName(employee.getLastName());
            employeeRepository.save(employee);
        }
        return findEmployee.get();
    }

    @Override
    @Transactional
    public void delete(Employee employee){
        employeeRepository.delete(employee);
    }
}
