package com.lecture11.assignment1.service;

import com.lecture11.assignment1.model.Salary;
import com.lecture11.assignment1.model.composite.SalaryId;

public interface SalaryService {
    
    Salary save(Salary salary);
    Salary findById(SalaryId id);
    Salary update(SalaryId id, Salary salary);
    void delete(SalaryId id);
    
}
