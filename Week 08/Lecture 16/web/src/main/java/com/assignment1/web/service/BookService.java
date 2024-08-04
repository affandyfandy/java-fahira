package com.assignment1.web.service;

import com.assignment1.web.dto.request.CreateBookDto;
import com.assignment1.web.dto.response.ReadBookDto;
import java.util.List;

public interface BookService {
    ReadBookDto create(CreateBookDto dto);
    List<ReadBookDto> findAll();
    ReadBookDto findById(Integer id);
}
