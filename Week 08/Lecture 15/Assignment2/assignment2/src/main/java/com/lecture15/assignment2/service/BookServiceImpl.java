package com.lecture15.assignment2.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lecture15.assignment2.data.entity.Book;
import com.lecture15.assignment2.data.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Integer id, Book book) {
        Optional<Book> findBook = findById(id);
        if (findBook.isPresent()){
            Book getBook = findBook.get();
            getBook.setTitle(book.getTitle());
            getBook.setWriter(book.getWriter());
            bookRepository.save(getBook);
            return getBook;
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        Optional<Book> findBook = findById(id);
        if (findBook.isPresent()){
            Book getBook = findBook.get();
            bookRepository.delete(getBook);
        }
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }
    
}
