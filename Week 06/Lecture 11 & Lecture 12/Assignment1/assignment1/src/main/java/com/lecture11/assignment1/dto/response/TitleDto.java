package com.lecture11.assignment1.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleDto {
    private int employeeNo;
    private String title;
    private LocalDate fromDate;
    private LocalDate toDate;
}
