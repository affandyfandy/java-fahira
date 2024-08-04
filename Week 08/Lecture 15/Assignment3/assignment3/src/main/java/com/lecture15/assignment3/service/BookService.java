package com.lecture15.assignment3.service;

import java.util.Optional;

import com.lecture15.assignment3.data.entity.Book;

public interface BookService {
    Optional<Book> findById(Integer id);
    Book save(Book book);
    Book update(Integer id, Book book);
    void delete(Integer id);
}
