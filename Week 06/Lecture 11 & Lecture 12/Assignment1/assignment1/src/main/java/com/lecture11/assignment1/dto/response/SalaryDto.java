package com.lecture11.assignment1.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto {
    private int employeeNo;
    private Integer salary;
    private LocalDate fromDate;
    private LocalDate toDate;
}
