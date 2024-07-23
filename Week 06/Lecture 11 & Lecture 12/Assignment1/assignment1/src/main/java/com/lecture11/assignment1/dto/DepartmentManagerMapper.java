package com.lecture11.assignment1.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.lecture11.assignment1.dto.request.CreateDepartmentManagerDto;
import com.lecture11.assignment1.dto.response.ReadDepartmentManagerDto;
import com.lecture11.assignment1.model.DepartmentManager;

@Mapper(componentModel="spring")
public interface DepartmentManagerMapper {
    @Mapping(target = "department.deptNo", source = "department")
    @Mapping(target = "employee.employeeNo", source = "employee")
    DepartmentManager toEntity(CreateDepartmentManagerDto deptManagerDto);
    
    @Mapping(target = "department", source = "department.deptNo")
    @Mapping(target = "employee", source = "employee.employeeNo")
    ReadDepartmentManagerDto toDto(DepartmentManager deptManagers);
}
