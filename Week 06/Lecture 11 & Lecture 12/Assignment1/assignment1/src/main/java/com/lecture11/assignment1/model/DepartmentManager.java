package com.lecture11.assignment1.model;

import java.time.LocalDate;

import com.lecture11.assignment1.model.composite.DepartmentManagerId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="DepartmentManager")
@IdClass(DepartmentManagerId.class)
public class DepartmentManager {

    @Id
    @ManyToOne
    @JoinColumn(name = "dept_no", referencedColumnName = "dept_no", nullable = false)
    private Department department;

    @Id
    @ManyToOne
    @JoinColumn(name = "employee_no", referencedColumnName = "employee_no", nullable = false)
    private Employee employee;
    
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}
