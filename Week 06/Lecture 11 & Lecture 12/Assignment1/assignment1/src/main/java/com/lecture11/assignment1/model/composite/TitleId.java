package com.lecture11.assignment1.model.composite;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

@Data
public class TitleId implements Serializable{
    
    private int employee;
    private String title;
    private LocalDate fromDate;

}
