package com.assignment1.book.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.assignment1.book.data.entity.Book;
import com.assignment1.book.data.repository.BookRepository;
import com.assignment1.book.dto.ReadWriterDto;
import com.assignment1.book.exception.ObjectNotFoundException;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final WebClient webClient;

    public BookServiceImpl(BookRepository bookRepository, WebClient.Builder webClientBuilder) {
        this.bookRepository = bookRepository;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @Override
    @Transactional
    public Book save(Book book) {
        findWriterById(book.getWriterId());
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book findById(Integer id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException(String.format("Book with Id %s is not found", id)));
    }

    @Override
    @Transactional
    public Book update(Integer id, Book book) {
        Book findBook = findById(id);
        
        if (book.getWriterId() != null){
            findWriterById(id);
        }

        findBook.setPublishedDate(book.getPublishedDate() != null ? book.getPublishedDate() : findBook.getPublishedDate());
        findBook.setTitle(book.getTitle() != null ? book.getTitle() : findBook.getTitle());
        findBook.setWriterId(book.getWriterId() != null ? book.getWriterId() : findBook.getWriterId());

        return bookRepository.save(findBook);
    }

    @Override
    @Transactional
    public List<Book> search(String title, Integer writerId){
        if (title.isBlank() && writerId != 0){
            return bookRepository.findAllByWriterId(writerId);
        } else if (!title.isBlank() && writerId == 0){
            return bookRepository.findAllByTitle(title);
        } else if (!title.isBlank() && writerId != 0){
            return bookRepository.findAllByTitleAndWriterId(writerId, title);
        } else{
            return bookRepository.findAll();
        }
    }

    private ReadWriterDto findWriterById(Integer id) {
        try{
            return webClient
                .get()
                .uri("/api/v1/writer/{id}",id)
                .retrieve()
                .bodyToMono(ReadWriterDto.class)
                .block();
        }
        catch (Exception e){
            throw new ObjectNotFoundException(String.format("Writer with Id %s is not found", id));
        }
    }
    
}
