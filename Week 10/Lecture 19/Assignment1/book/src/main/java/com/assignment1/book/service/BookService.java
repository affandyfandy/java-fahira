package com.assignment1.book.service;

import java.util.List;

import com.assignment1.book.data.entity.Book;

public interface BookService {
    
    Book save(Book book);
    List<Book> findAll();
    Book findById(Integer id);
}
