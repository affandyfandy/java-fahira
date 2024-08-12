package com.assignment1.writer.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;

import com.assignment1.writer.data.entity.Writer;
import com.assignment1.writer.dto.CreateWriterDto;
import com.assignment1.writer.dto.ReadWriterDto;
import com.assignment1.writer.dto.WriterMapper;
import com.assignment1.writer.service.WriterService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/writer")
public class WriterController {

    private final WriterMapper writerMapper;
    private final WriterService writerService;
    
    public WriterController(WriterService writerService, WriterMapper writerMapper){
        this.writerService = writerService;
        this.writerMapper = writerMapper;
    }

    @PostMapping
    public ResponseEntity<ReadWriterDto> createNewWriter(@RequestBody CreateWriterDto dto) {
        Writer writer = writerMapper.toEntity(dto);
        Writer newWriter = writerService.save(writer);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(writerMapper.toDto(newWriter));
    }

    @GetMapping
    public ResponseEntity<List<ReadWriterDto>> findAll() {
        List<Writer> listWriter = writerService.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(writerMapper.toListDto(listWriter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadWriterDto> findById(@PathVariable("id") Integer id) {
        Writer findWriter = writerService.findById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(writerMapper.toDto(findWriter));
    }
    

}
