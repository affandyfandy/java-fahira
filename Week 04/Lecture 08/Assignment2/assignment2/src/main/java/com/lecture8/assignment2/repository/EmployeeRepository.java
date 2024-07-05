package com.lecture8.assignment2.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lecture8.assignment2.entity.Employee;

@Repository
public class EmployeeRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public int save(Employee employee){
        return jdbc.update("INSERT INTO employee (id, name, dob, address, department) VALUES (?,?,?,?,?)",
            new Object[] { employee.getId(), employee.getName(), employee.getDob(), employee.getAddress(), employee.getDepartment() });
    }

    public int update(String id, Employee employee){
        return jdbc.update("UPDATE employee SET name=?, dob=?, address=?, department=? WHERE id=?",
            new Object[] { employee.getName(), employee.getDob(),employee.getAddress(), employee.getDepartment(), id});
    }

    public Employee findById(String id){
        try {
            Employee employee = jdbc.queryForObject("SELECT * FROM employee WHERE id=? LIMIT 1",
                    BeanPropertyRowMapper.newInstance(Employee.class), id);
            return employee;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public int deleteById(String id) {
        return jdbc.update("DELETE FROM employee WHERE id=?", id);
    }

    public List<Employee> findAll(){
        return jdbc.query("SELECT * from employee", BeanPropertyRowMapper.newInstance(Employee.class));
    }

}
