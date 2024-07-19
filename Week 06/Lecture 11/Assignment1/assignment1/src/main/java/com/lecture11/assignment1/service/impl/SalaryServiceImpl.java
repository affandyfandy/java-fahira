package com.lecture11.assignment1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lecture11.assignment1.model.Salary;
import com.lecture11.assignment1.model.composite.SalaryId;
import com.lecture11.assignment1.repository.SalaryRepository;
import com.lecture11.assignment1.service.SalaryService;
import jakarta.transaction.Transactional;

@Service
public class SalaryServiceImpl implements SalaryService {
    
    @Autowired
    SalaryRepository salaryRepository;

    @Override
    @Transactional
    public Salary save(Salary salary){
        salaryRepository.save(salary);
        return salary;
    }

    @Override
    @Transactional
    public Salary findById(SalaryId id) {
        return salaryRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Salary update(SalaryId id, Salary salary) {
        var findSalary = salaryRepository.findById(id);
        if (findSalary.isPresent()){
            Salary slr = findSalary.get();
            slr.setSalary(salary.getSalary());
            slr.setToDate(salary.getToDate());
            salaryRepository.save(slr);
        }
        return salaryRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void delete(SalaryId id) {
        salaryRepository.delete(findById(id));
    }

}
