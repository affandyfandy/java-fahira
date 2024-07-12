package com.lecture10.assignment1.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private String id;
    private String name;
    private String address;
    private String department;
    private String email;
    private LocalDate dob;
    private String phone;

}
