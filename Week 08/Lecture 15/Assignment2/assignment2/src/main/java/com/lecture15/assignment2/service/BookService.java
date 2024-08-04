package com.lecture15.assignment2.service;

import java.util.Optional;

import com.lecture15.assignment2.data.entity.Book;

public interface BookService {
    Optional<Book> findById(Integer id);
    Book save(Book book);
    Book update(Integer id, Book book);
    void delete(Integer id);
}
