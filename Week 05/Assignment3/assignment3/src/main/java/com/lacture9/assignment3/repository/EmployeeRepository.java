package com.lacture9.assignment3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.lacture9.assignment3.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{

    List<Employee> findAllByOrderByNameAsc();

    @Query(value = "SELECT MAX(salary) FROM employee", nativeQuery = true)
    Optional<Integer> findMaxSalary();

    @Query(value = "SELECT MIN(salary) FROM employee", nativeQuery = true)
    Optional<Integer> findMinSalary();

    @Query(value = "SELECT AVG(salary) FROM employee", nativeQuery = true)
    Double findAverageSalary();

    @Query(value = "SELECT e.name " +
                   "FROM employee e " +
                   "WHERE e.salary = (SELECT MAX(salary) FROM employee)", nativeQuery = true)
    Optional<String> findEmployeeNameWithHighestSalary();

    @Query(value = "SELECT e.name " +
                   "FROM employee e " +
                   "WHERE e.salary = (SELECT MIN(salary) FROM employee)", nativeQuery = true)
    Optional<String> findEmployeeNameWithLowestSalary();
    
}
