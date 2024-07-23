package com.lecture8.assignment3.service;

import java.util.List;
import com.lecture8.assignment3.entity.Employee;

public interface EmployeeService {

    Employee saveToDb1(Employee employee);
    Employee saveToDb2(Employee employee);
    List<Employee> findAllOnDb1();
    List<Employee> findAllOnDb2();
    Employee findEmployeeByIdOnDb1(String id);
    Employee findEmployeeByIdOnDb2(String id);
    Employee updateOnDb1(Employee employee);
    Employee updateOnDb2(Employee employee);
    String deleteOnDb1(String id);
    String deleteOnDb2(String id);

}
