package com.lecture15.assignment3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lecture15.assignment3.data.entity.Book;
import com.lecture15.assignment3.service.BookService;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createNewBook(HttpServletResponse response, @RequestBody Book book) {
        Book savedBook = bookService.save(book);
        System.out.println(response.getHeader("user-name"));
        return ResponseEntity.ok()
                             .header("source", "fpt-software")
                             .body(savedBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(HttpServletResponse response, @PathVariable("id") Integer id) {
        Optional<Book> book = bookService.findById(id);
        System.out.println(response.getHeader("user-name"));
        return book.map(b -> ResponseEntity.ok().header("source", "fpt-software").body(b))
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(HttpServletResponse response, @PathVariable("id") Integer id, @RequestBody Book book) {
        Book updatedBook = bookService.update(id, book);
        System.out.println(response.getHeader("user-name"));
        return ResponseEntity.ok().header("source", "fpt-software").body(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(HttpServletResponse response, @PathVariable("id") Integer id) {
        System.out.println(response.getHeader("user-name"));
        bookService.delete(id);
        return ResponseEntity.noContent().header("source", "fpt-software").build();
    }
}
