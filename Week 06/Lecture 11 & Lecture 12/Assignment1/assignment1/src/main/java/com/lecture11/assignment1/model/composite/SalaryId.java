package com.lecture11.assignment1.model.composite;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class SalaryId implements Serializable {

    private int employee;
    private LocalDate fromDate;

}
