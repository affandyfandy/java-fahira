package com.lecture11.assignment1.model;

import java.time.LocalDate;

import com.lecture11.assignment1.model.composite.SalaryId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
public class Salary {

    @EmbeddedId
    @Column(name = "id")
    private SalaryId id;

    @Column(name = "salary", length = 11, nullable = false)
    private Integer salary;
    
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;
    
}
