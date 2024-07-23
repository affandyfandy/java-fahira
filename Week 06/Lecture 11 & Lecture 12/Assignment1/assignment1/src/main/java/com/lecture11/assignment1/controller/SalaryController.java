package com.lecture11.assignment1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.lecture11.assignment1.dto.SalaryMapper;
import com.lecture11.assignment1.dto.response.SalaryDto;
import com.lecture11.assignment1.model.Salary;
import com.lecture11.assignment1.service.EmployeeService;
import com.lecture11.assignment1.service.SalaryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private SalaryMapper salaryMapper;

    @Autowired 
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<SalaryDto> saveSalary(@RequestBody SalaryDto salaryDto){
        Salary salary = salaryMapper.toEntity(salaryDto);
        salary.setEmployee(employeeService.findById(salaryDto.getEmployeeNo()));
        Salary newSalary = salaryService.save(salary);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(salaryMapper.toDto(newSalary));
    }

    @GetMapping
    public ResponseEntity<List<SalaryDto>> getAllSalary() {
        List<SalaryDto> listSalary = salaryMapper.toListDto(salaryService.findAll());
        return ResponseEntity.status(HttpStatus.OK).body(listSalary);
    }
    
}
