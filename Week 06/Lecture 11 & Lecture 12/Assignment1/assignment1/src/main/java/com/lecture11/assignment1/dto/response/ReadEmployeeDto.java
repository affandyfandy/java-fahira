package com.lecture11.assignment1.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadEmployeeDto {
    private int employeeNo;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private LocalDate hireDate;
    private String gender;
    private List<SalaryDto> listSalary;
    private List<TitleDto> listTitle;
}
