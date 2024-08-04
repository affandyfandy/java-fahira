package com.assignment1.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.assignment1.web.client.WriterClient;
import com.assignment1.web.dto.request.CreateWriterDto;
import com.assignment1.web.dto.response.ReadWriterDto;

@Service
public class WriterServiceImpl implements WriterService {

    private final WebClient webClient;
    private final RestTemplate restTemplate;
    private final WriterClient writerClient;

    public WriterServiceImpl(WebClient.Builder webClientBuilder, 
                           RestTemplate restTemplate, 
                           WriterClient writerClient) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082").build();
        this.restTemplate = restTemplate;
        this.writerClient = writerClient;
    }

    @Override
    @Transactional
    public ReadWriterDto findById(Integer id) {
        String url = "http://localhost:8082/api/v1/writer/" + id;
        return restTemplate.getForObject(url, ReadWriterDto.class);
    }

    @Override
    @Transactional
    public ReadWriterDto create(CreateWriterDto dto) {
        return writerClient.save(dto);
    }

    @Override
    @Transactional
    public List<ReadWriterDto> findAll() {
        return webClient
                .get()
                .uri("/api/v1/writer")
                .retrieve()
                .bodyToFlux(ReadWriterDto.class)
                .collectList()
                .block();
    }
}
