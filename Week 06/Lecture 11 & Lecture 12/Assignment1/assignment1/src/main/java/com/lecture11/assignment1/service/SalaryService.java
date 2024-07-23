package com.lecture11.assignment1.service;

import java.util.List;
import com.lecture11.assignment1.model.Salary;

public interface SalaryService {
    
    Salary save(Salary salary);
    List<Salary> findAll();
}
