# Project Documentation

## Overview
This project is a microservices-based system consisting of multiple services interacting through RESTful APIs and Feign clients. The core services include a **Book** service and a **Writer** service. These services are designed to manage books and writers, respectively, and communicate with each other to complete operations.

## Microservice Architecture

### Book Service

**Description:** Manages book-related operations such as creating and retrieving books.

**Endpoints:**
- `POST /api/v1/book`: Create a new book.
- `GET /api/v1/book/{id}`: Retrieve a book by ID.
- `GET /api/v1/book`: Retrieve all books.
    
**Port:** 8081

### Writer Service

**Description:** Manages writer-related operations such as creating and retrieving writers.
    
**Endpoints:**
- `POST /api/v1/writer`: Create a new writer.
- `GET /api/v1/writer/{id}`: Retrieve a writer by ID.
- `GET /api/v1/writer`: Retrieve all writers.

**Port:** 8082

## Implementation

1. Spring Cloud OpenFeign

```java
@FeignClient(name = "bookClient", url = "${book.api.url}")
public interface BookClient {
    @GetMapping("/api/v1/book/{id}")
    ReadBookDto findByBookId(@PathVariable Integer id);
}
```
**Dependencies:** `spring-cloud-starter-openfeign`

2. Spring WebClient

```java
WebClient webClient = WebClient.builder()
    .baseUrl("http://localhost:8081")
    .build();

public ReadBookDto getBook(Integer id) {
    return webClient
        .get()
        .uri("/api/v1/book/{id}", id)
        .retrieve()
        .bodyToMono(ReadBookDto.class)
        .block();
}
```
**Dependencies:** `spring-boot-starter-webflux`

3. Spring RestTemplate

```java
RestTemplate restTemplate = new RestTemplate();

public ReadBookDto getBook(Integer id) {
    String url = "http://localhost:8081/api/v1/book/" + id;
    return restTemplate.getForObject(url, ReadBookDto.class);
}
```
**Dependencies:**   `spring-boot-starter-web`