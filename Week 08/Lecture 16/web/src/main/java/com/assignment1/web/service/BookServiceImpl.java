package com.assignment1.web.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.assignment1.web.client.BookClient;
import com.assignment1.web.dto.request.CreateBookDto;
import com.assignment1.web.dto.response.ReadBookDto;
import com.assignment1.web.dto.response.ReadWriterDto;

@Service
public class BookServiceImpl implements BookService{

    private final WriterService writerService;
    private final WebClient webClient;
    private final RestTemplate restTemplate;
    private final BookClient bookClient;

    public BookServiceImpl(WebClient.Builder webClientBuilder, 
                           RestTemplate restTemplate, 
                           BookClient bookClient, 
                           WriterService writerService) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
        this.restTemplate = restTemplate;
        this.bookClient = bookClient;
        this.writerService = writerService;
    }

    @Override
    @Transactional
    public ReadBookDto create(CreateBookDto dto) {
        ReadWriterDto writerDto = writerService.findById(dto.getWriterId());
        if (writerDto == null) return null;

        ReadBookDto response = webClient
                .post()
                .uri("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ReadBookDto.class)
                .block();
        return response;
    }

    @Override
    @Transactional
    public List<ReadBookDto> findAll() {
        String url = "http://localhost:8081/api/v1/book";
        ReadBookDto[] responseArray = restTemplate.getForObject(url, ReadBookDto[].class);
        return Arrays.asList(responseArray);
    }

    @Override
    @Transactional
    public ReadBookDto findById(Integer id) {
        return bookClient.findByBookId(id);
    }
}
