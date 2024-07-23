package com.lecture11.assignment1.model.composite;

import java.io.Serializable;
import lombok.Data;

@Data
public class DepartmentManagerId implements Serializable {
    
    private int employee;
    private String department;
    
}
