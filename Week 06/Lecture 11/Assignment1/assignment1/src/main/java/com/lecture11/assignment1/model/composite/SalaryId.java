package com.lecture11.assignment1.model.composite;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor 
public class SalaryId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int employeeNo;
    private LocalDate fromDate;

    public SalaryId(int employeeNo, LocalDate fromDate){
        this.employeeNo = employeeNo;
        this.fromDate = fromDate;
    }

}
