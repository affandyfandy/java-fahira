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
public class TitleId implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private int employeeNo;
    private String title;
    private LocalDate fromDate;

    public TitleId(int employeeNo, String title, LocalDate fromDate){
        this.employeeNo = employeeNo;
        this.title = title;
        this.fromDate = fromDate;
    }

}
