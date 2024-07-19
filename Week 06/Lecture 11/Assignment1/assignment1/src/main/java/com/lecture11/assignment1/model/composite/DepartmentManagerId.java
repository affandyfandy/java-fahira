package com.lecture11.assignment1.model.composite;

import java.io.Serializable;


import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Embeddable
@EqualsAndHashCode
public class DepartmentManagerId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int employeeNo;
    private String deptNo;

    public DepartmentManagerId(int employeeNo, String deptNo){
        this.employeeNo = employeeNo;
        this.deptNo = deptNo;
    }

}
