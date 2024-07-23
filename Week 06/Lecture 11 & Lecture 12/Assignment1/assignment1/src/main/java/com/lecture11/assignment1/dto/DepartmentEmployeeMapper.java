package com.lecture11.assignment1.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lecture11.assignment1.dto.request.CreateDepartmentEmployeeDto;
import com.lecture11.assignment1.dto.response.ReadDepartmentEmployeeDto;
import com.lecture11.assignment1.model.DepartmentEmployee;

@Mapper(componentModel="spring")
public interface DepartmentEmployeeMapper {
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "employee", ignore = true)
    DepartmentEmployee toEntity(CreateDepartmentEmployeeDto deptEmpDto);

    @Mapping(target = "department", source = "department.deptNo")
    @Mapping(target = "employee", source = "employee.employeeNo")
    ReadDepartmentEmployeeDto toDto(DepartmentEmployee departmentEmployee);
}
