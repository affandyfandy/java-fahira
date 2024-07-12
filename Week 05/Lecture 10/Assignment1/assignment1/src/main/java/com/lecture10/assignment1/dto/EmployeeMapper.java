package com.lecture10.assignment1.dto;

import org.mapstruct.Mapper;
import java.util.List;
import com.lecture10.assignment1.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO toDTO(Employee employee);
    List<EmployeeDTO> toListDTO(List<Employee> listEmployees);
    List<Employee> toList(List<EmployeeDTO> listEmployeeDTO);
    
}
