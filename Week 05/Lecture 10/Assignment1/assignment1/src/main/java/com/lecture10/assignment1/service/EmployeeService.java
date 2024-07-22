package com.lecture10.assignment1.service;

import com.lecture10.assignment1.dto.EmployeeDTO;
import java.util.List;

public interface EmployeeService {
    
    void save(EmployeeDTO employeeDTO);
    EmployeeDTO update(EmployeeDTO employeeDTO, String id);
    List<EmployeeDTO> listAllEmployee();
    void delete(String id);
    EmployeeDTO findById(String id);
    List<EmployeeDTO> findByDepartment(String department);
    void saveAll(List<EmployeeDTO> listEmployeeDTO);
   
}
