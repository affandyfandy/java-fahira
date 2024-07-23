package com.lecture11.assignment1.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lecture11.assignment1.model.Salary;
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
    public List<Salary> findAll() {
        return salaryRepository.findAll();
    }

}
