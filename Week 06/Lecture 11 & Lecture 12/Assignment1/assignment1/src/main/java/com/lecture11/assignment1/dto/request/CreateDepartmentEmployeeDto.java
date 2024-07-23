package com.lecture11.assignment1.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDepartmentEmployeeDto {
    private String department;
    private int employee;
    private LocalDate fromDate;
    private LocalDate toDate;
}
