package com.lecture11.assignment1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lecture11.assignment1.model.Salary;
import com.lecture11.assignment1.model.composite.SalaryId;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, SalaryId>{

}
