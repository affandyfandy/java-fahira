package com.lecture11.assignment1.controller;

import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import com.lecture11.assignment1.model.Salary;
import com.lecture11.assignment1.model.composite.SalaryId;
import com.lecture11.assignment1.service.SalaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping
    public ResponseEntity<Salary> saveSalary(@RequestBody Salary salary){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(salaryService.save(salary));
    }

    @GetMapping("/employeeId={employeeId}&fromDate={date}")
    public ResponseEntity<Salary> getSalaryById(@PathVariable int employeeId, @PathVariable String date) {
        LocalDate fromDate = LocalDate.parse(date);
        SalaryId id = new SalaryId(employeeId, fromDate);
        return ResponseEntity.status(HttpStatus.OK).body(salaryService.findById(id));
    }

    @PutMapping("/employeeId={employeeId}&fromDate={date}")
    public ResponseEntity<Salary> updateSalary(@PathVariable int employeeId, @PathVariable String date, @RequestBody Salary salary) {
        LocalDate fromDate = LocalDate.parse(date);
        SalaryId id = new SalaryId(employeeId, fromDate);
        return ResponseEntity.status(HttpStatus.OK).body(salaryService.update(id, salary));
    }

    @DeleteMapping("/employeeId={employeeId}&fromDate={date}")
    public ResponseEntity<String> deleteSalary(@PathVariable int employeeId, @PathVariable String date) {
        LocalDate fromDate = LocalDate.parse(date);
        SalaryId id = new SalaryId(employeeId, fromDate);
        salaryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete successful!");
    }

}
