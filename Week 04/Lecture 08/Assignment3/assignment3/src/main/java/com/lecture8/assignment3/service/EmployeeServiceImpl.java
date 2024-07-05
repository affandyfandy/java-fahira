package com.lecture8.assignment3.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lecture8.assignment3.entity.Employee;
import com.lecture8.assignment3.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveToDb1(Employee employee) {
        int res = employeeRepository.saveToDb1(employee);
        if (res > 0){
            return employee;
        }
        return null;
    }

    @Override
    public Employee saveToDb2(Employee employee) {
        int res = employeeRepository.saveToDb2(employee);
        if (res > 0){
            return employee;
        }
        return null;
    }

    @Override
    public List<Employee> findAllOnDb1() {
        var listEmployee = employeeRepository.findAllOnDb1();
        return listEmployee;
    }

    @Override
    public List<Employee> findAllOnDb2() {
        var listEmployee = employeeRepository.findAllOnDb2();
        return listEmployee;
    }

    @Override
    public Employee findEmployeeByIdOnDb1(String id) {
        var employee = employeeRepository.findByIdOnDb1(id);
        if (employee != null){
            return employee;
        }
        return null;
    }

    @Override
    public Employee findEmployeeByIdOnDb2(String id) {
        var employee = employeeRepository.findByIdOnDb2(id);
        if (employee != null){
            return employee;
        }
        return null;
    }

    @Override
    public Employee updateOnDb1(Employee employee) {
        int res = employeeRepository.updateOnDb1(employee);
        if (res > 0){
            return employee;
        }
        return null;
    }

    @Override
    public Employee updateOnDb2(Employee employee) {
        int res = employeeRepository.updateOnDb2(employee);
        if (res > 0){
            return employee;
        }
        return null;
    }

    @Override
    public String deleteOnDb1(String id) {
        int res = employeeRepository.deleteByIdOnDb1(id);
        if (res > 0){
            return "success";
        }
        return "failed";
    }

    @Override
    public String deleteOnDb2(String id) {
        int res = employeeRepository.deleteByIdOnDb2(id);
        if (res > 0){
            return "success";
        }
        return "failed";
    }
}
