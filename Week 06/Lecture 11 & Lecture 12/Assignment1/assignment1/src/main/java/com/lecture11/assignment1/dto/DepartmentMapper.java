package com.lecture11.assignment1.dto;

import java.util.List;

import org.mapstruct.Mapper;

import com.lecture11.assignment1.dto.request.CreateDepartmentDto;
import com.lecture11.assignment1.dto.response.ReadDepartmentDto;
import com.lecture11.assignment1.model.Department;

@Mapper(componentModel = "spring", uses = {DepartmentEmployeeMapper.class, DepartmentManagerMapper.class})
public interface DepartmentMapper {
    ReadDepartmentDto toDto(Department dept);
    Department toEntity(CreateDepartmentDto departmentDto);
    List<ReadDepartmentDto> toListDto(List<Department> depts);
}
