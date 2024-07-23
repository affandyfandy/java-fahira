package com.lecture11.assignment1.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDto {
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private LocalDate hireDate;
    private String gender;   
}
