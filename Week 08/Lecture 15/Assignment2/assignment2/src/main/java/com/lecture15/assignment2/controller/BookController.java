package com.lecture15.assignment2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lecture15.assignment2.data.entity.Book;
import com.lecture15.assignment2.service.BookService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createNewBook(@RequestBody Book book) {
        Book savedBook = bookService.save(book);
        return ResponseEntity.ok()
                             .header("source", "fpt-software")
                             .body(savedBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id) {
        Optional<Book> book = bookService.findById(id);
        return book.map(b -> ResponseEntity.ok().header("source", "fpt-software").body(b))
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Integer id, @RequestBody Book book) {
        Book updatedBook = bookService.update(id, book);
        return ResponseEntity.ok().header("source", "fpt-software").body(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id) {
        bookService.delete(id);
        return ResponseEntity.noContent().header("source", "fpt-software").build();
    }
}
