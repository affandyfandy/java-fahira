package com.lecture8.assignment2.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.lecture8.assignment2.entity.Employee;
import com.lecture8.assignment2.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        int res = employeeRepository.save(employee);
        if (res > 0){
            return employee;
        }
        return null;
    }

    @Override
    public List<Employee> findAll() {
        var listEmployee = employeeRepository.findAll();
        return listEmployee;
    }

    @Override
    public Employee findEmployeeById(String id) {
        var employee = employeeRepository.findById(id);
        if (employee != null){
            return employee;
        }
        return null;
    }

    @Override
    public Employee update(Employee employee) {
        int res = employeeRepository.update(employee);
        if (res > 0){
            return employee;
        }
        return null;
    }

    @Override
    public String delete(String id) {
        int res = employeeRepository.deleteById(id);
        if (res > 0){
            return "success";
        }
        return "failed";
    }
}
