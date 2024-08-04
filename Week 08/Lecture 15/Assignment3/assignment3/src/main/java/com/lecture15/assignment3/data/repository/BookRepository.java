package com.lecture15.assignment3.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lecture15.assignment3.data.entity.Book;



@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
