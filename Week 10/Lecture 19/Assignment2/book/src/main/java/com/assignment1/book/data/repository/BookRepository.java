package com.assignment1.book.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.assignment1.book.data.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
    
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    List<Book> findAllByTitle(@Param("title") String title);

    @Query("SELECT b FROM Book b WHERE b.writerId = :writerId")
    List<Book> findAllByWriterId(@Param("writerId") Integer writerId);

    @Query("SELECT b FROM Book b WHERE b.writerId = :writerId AND b.title LIKE %:title%")
    List<Book> findAllByTitleAndWriterId(@Param("writerId") Integer writerId, @Param("title") String title);

}
