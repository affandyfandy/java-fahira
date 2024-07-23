package com.lecture11.assignment1.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import com.lecture11.assignment1.dto.response.SalaryDto;
import com.lecture11.assignment1.model.Salary;

@Mapper(componentModel = "spring")
public interface SalaryMapper {
    @Mapping(target = "employee.employeeNo", source = "employeeNo")
    Salary toEntity(SalaryDto salaryDto);

    @Mapping(target = "employeeNo", source = "employee.employeeNo")
    SalaryDto toDto(Salary salary);

    List<SalaryDto> toListDto(List<Salary> listSalary);
}
