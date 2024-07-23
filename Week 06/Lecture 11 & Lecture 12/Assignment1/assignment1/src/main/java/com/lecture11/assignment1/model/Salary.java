package com.lecture11.assignment1.model;

import java.time.LocalDate;

import com.lecture11.assignment1.model.composite.SalaryId;

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
@Table(name="Salary")
@IdClass(SalaryId.class)
public class Salary {

    @Id
    @ManyToOne
    @JoinColumn(name = "employee_no", referencedColumnName = "employee_no", nullable = false)
    private Employee employee;

    @Id
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "salary", length = 11, nullable = false)
    private Integer salary;
    
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

}
