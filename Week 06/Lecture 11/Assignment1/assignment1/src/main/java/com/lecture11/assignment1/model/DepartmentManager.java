package com.lecture11.assignment1.model;

import java.time.LocalDate;

import com.lecture11.assignment1.model.composite.DepartmentManagerId;

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
@Table(name="DepartmentManager")
public class DepartmentManager {
    
    @EmbeddedId
    @Column(name = "id")
    private DepartmentManagerId id;
    
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}
