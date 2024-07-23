package com.lecture11.assignment1.dto;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.lecture11.assignment1.dto.response.TitleDto;
import com.lecture11.assignment1.model.Title;

@Mapper(componentModel = "spring")
public interface TitleMapper {
    @Mapping(target = "employee.employeeNo", source = "employeeNo")
    Title toEntity(TitleDto titleDto);

    @Mapping(target = "employeeNo", source = "employee.employeeNo")
    TitleDto toDto(Title title);

    List<TitleDto> toListDto(List<Title> listTitle);
}
