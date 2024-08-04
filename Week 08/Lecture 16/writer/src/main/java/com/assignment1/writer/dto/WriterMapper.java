package com.assignment1.writer.dto;

import java.util.List;

import org.mapstruct.Mapper;
import com.assignment1.writer.data.entity.Writer;

@Mapper(componentModel = "spring")
public interface WriterMapper {
    
    Writer toEntity(CreateWriterDto dto);
    ReadWriterDto toDto(Writer writer);
    List<ReadWriterDto> toListDto(List<Writer> listWriter);
}
