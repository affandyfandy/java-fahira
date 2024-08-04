package com.assignment1.web.service;

import com.assignment1.web.dto.request.CreateWriterDto;
import com.assignment1.web.dto.response.ReadWriterDto;
import java.util.List;

public interface WriterService {
    ReadWriterDto findById(Integer id);
    ReadWriterDto create(CreateWriterDto dto);
    List<ReadWriterDto> findAll();
}   
