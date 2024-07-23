package com.lecture11.assignment1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.lecture11.assignment1.model.Employee;
import com.lecture11.assignment1.model.Salary;
import com.lecture11.assignment1.model.Title;
import com.lecture11.assignment1.repository.EmployeeRepository;
import com.lecture11.assignment1.service.EmployeeService;
import com.lecture11.assignment1.service.SalaryService;
import com.lecture11.assignment1.service.TitleService;
import com.lecture11.assignment1.specification.EmployeeSpecification;
import com.lecture11.assignment1.specification.SearchCriteria;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    SalaryService salaryService;

    @Autowired
    TitleService titleService;

    @Override
    @Transactional
    public Page<Employee> findAll(Pageable pageable){
        return employeeRepository.findAll(pageable);
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
            emp.setBirthDate(employee.getBirthDate());
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

    @Override
    @Transactional
    public Employee updateSalary(Employee employee, Salary salary) {
        Salary newSalary = salaryService.save(salary);
        newSalary.setEmployee(employee);
        List<Salary> listSalary = employee.getListSalary();
        listSalary.add(salary);
        return employee;
    }

    @Override
    @Transactional
    public Employee updateTitle(Employee employee, Title title) {
        Title newTitle = titleService.save(title);
        newTitle.setEmployee(employee);
        List<Title> listTitle = employee.getListTitle();
        listTitle.add(title);
        return employee;
    }

    @Override
    @Transactional
    public Page<Employee> searchEmployees(List<SearchCriteria> params, Pageable pageable) {
        EmployeeSpecification spec = new EmployeeSpecification();
        params.forEach(spec::add);
        return employeeRepository.findAll(spec, pageable);
    }
}
