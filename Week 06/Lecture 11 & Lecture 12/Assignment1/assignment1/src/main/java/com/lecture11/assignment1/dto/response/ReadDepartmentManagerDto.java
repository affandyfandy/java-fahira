package com.lecture11.assignment1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadDepartmentManagerDto {
    private String department;
    private int employee;
    private LocalDate fromDate;
    private LocalDate toDate;
}
