package com.lacture9.assignment3.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lacture9.assignment3.model.Employee;
import com.lacture9.assignment3.service.EmployeeService;
import com.lacture9.assignment3.utils.DocumentUtils;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeRestController {
    
    private final DocumentUtils documentUtils;
    private final EmployeeService employeeService;

    @GetMapping("/download")
    public ResponseEntity<?> download() throws java.io.IOException {
        List<Employee> employees = employeeService.findAll();
        byte[] pdfBytes = documentUtils.generateEmployeeInfo(employees);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employee-info.pdf")
            .body(pdfBytes);
    }
}
