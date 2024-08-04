package com.assignment1.book.dto;

import org.mapstruct.Mapper;
import java.util.List;
import com.assignment1.book.data.entity.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
    
    Book toEntity(CreateBookDto dto);

    ReadBookDto toDto(Book book);

    List<ReadBookDto> toListDto(List<Book> book);
}
