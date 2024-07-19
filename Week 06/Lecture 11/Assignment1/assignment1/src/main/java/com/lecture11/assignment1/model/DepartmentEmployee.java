package com.lecture11.assignment1.model;


import java.time.LocalDate;

import com.lecture11.assignment1.model.composite.DepartmentEmployeeId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "DepartmentEmployee")
public class DepartmentEmployee {
   
    @EmbeddedId
    @Column(name = "id")
    private DepartmentEmployeeId id;

    @Temporal(TemporalType.DATE)
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date")
    private LocalDate toDate;

}
