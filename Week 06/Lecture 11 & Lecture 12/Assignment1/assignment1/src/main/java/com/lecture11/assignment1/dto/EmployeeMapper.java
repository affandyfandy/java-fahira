package com.lecture11.assignment1.dto;

import org.mapstruct.Mapper;
import java.util.List;
import com.lecture11.assignment1.dto.request.CreateEmployeeDto;
import com.lecture11.assignment1.dto.response.ReadEmployeeDto;
import com.lecture11.assignment1.model.Employee;

@Mapper(componentModel = "spring", uses = {SalaryMapper.class, TitleMapper.class})
public interface EmployeeMapper {
    ReadEmployeeDto toDto(Employee employee);
    Employee toEntity(CreateEmployeeDto employeeDto);
    List<ReadEmployeeDto> toListDto(List<Employee> employee);
}
