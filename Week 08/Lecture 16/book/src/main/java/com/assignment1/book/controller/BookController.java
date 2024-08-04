package com.assignment1.book.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment1.book.data.entity.Book;
import com.assignment1.book.dto.BookMapper;
import com.assignment1.book.dto.CreateBookDto;
import com.assignment1.book.dto.ReadBookDto;
import com.assignment1.book.service.BookService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<ReadBookDto> createNewBook(@RequestBody CreateBookDto dto){
        Book book = bookMapper.toEntity(dto);
        Book newBook = bookService.save(book);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookMapper.toDto(newBook));
    }

    @GetMapping
    public ResponseEntity<List<ReadBookDto>> findAllBook(){
        List<Book> listBook = bookService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(bookMapper.toListDto(listBook));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadBookDto> findById(@PathVariable("id") Integer id){
        Book book = bookService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookMapper.toDto(book));
    }

}
