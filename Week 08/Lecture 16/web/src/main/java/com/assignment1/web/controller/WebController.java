package com.assignment1.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment1.web.dto.request.CreateBookDto;
import com.assignment1.web.dto.request.CreateWriterDto;
import com.assignment1.web.dto.response.ReadBookDto;
import com.assignment1.web.dto.response.ReadWriterDto;
import com.assignment1.web.service.BookService;
import com.assignment1.web.service.WriterService;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/library")
public class WebController {

    @Autowired
    private BookService bookService;

    @Autowired
    private WriterService writerService;

    @PostMapping("/book")
    public ResponseEntity<ReadBookDto> createNewBook(@RequestBody CreateBookDto dto) {
        ReadBookDto book = bookService.create(dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
    }

    @PostMapping("/writer")
    public ResponseEntity<ReadWriterDto> createNewWriter(@RequestBody CreateWriterDto dto) {
        ReadWriterDto book = writerService.create(dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
    }

    @GetMapping("/book")
    public ResponseEntity<List<ReadBookDto>> findAllBooks() {
        List<ReadBookDto> listBook = bookService.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(listBook);
    }

    @GetMapping("/writer")
    public ResponseEntity<List<ReadWriterDto>> findAllWriters() {
        List<ReadWriterDto> listWriter = writerService.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(listWriter);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<ReadBookDto> findBookById(@PathVariable("id") Integer id) {
        ReadBookDto book = bookService.findById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
    }

    @GetMapping("/writer/{id}")
    public ResponseEntity<ReadWriterDto> findWriterById(@PathVariable("id") Integer id) {
        ReadWriterDto writer = writerService.findById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(writer);
    }
}