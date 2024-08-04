package com.assignment1.book.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment1.book.data.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
    
}
