package com.lecture11.assignment1.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadDepartmentEmployeeDto { 
    private String department;
    private int employee;
    private LocalDate fromDate;
    private LocalDate toDate;
}
