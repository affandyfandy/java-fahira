# Spring cloud gateway custom filter

## Overview

This project is a Spring Cloud Gateway application that provides routing and filtering capabilities for microservices. It includes a custom `AuthenticationFilter` for validating API keys in incoming requests.

## Configuration

Edit [application.yaml](/api-gateway/src/main/resources/application.yaml)

```yaml
spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: book
          uri: http://localhost:8081/
          predicates:
            - Path=/api/v1/book/**
          filters:
            - name: AuthenticationFilter

        - id: writer
          uri: http://localhost:8082/
          predicates:
            - Path=/api/v1/writer/**
          filters:
            - name: AuthenticationFilter

server:
  port: 8080                
```

## Custom AuthenticationFilter

The `AuthenticationFilter` validates incoming requests by checking for an **api-key** header. If the API key is valid, the request proceeds; otherwise, an `UNAUTHORIZED` response is returned.

### Key components
- AuthenticationFilter

The `AuthenticationFilter` is a custom filter that validates incoming requests by checking for an **api-key** header. It extends `AbstractGatewayFilterFactory`, which provides a base class for implementing custom filters in Spring Cloud Gateway.

`AbstractGatewayFilterFactory` is a base class provided by Spring Cloud Gateway for creating custom filters. It allows us to define the filter logic and configuration. By extending this class, we can create filters that can be applied to routes. `apply` method defines the filter logic. It returns a `GatewayFilter` that processes the request and response. Within this method, we can implement custom behavior, such as checking headers, making external calls, or modifying the request or response.

#### Filter Logic

1. Checks if the request URI starts with `/api/v1/auth/generate` to bypass authentication for specific routes.
2. Validates the presence of the **api-key** header.
3. Makes an external call to validate the API key against a validation service.
4. Logs the response and errors for monitoring and debugging.
5. If validation fails, it returns an `UNAUTHORIZED` response.

```java
@Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            logger.info("Request URI: {}", request.getURI());

            if (request.getURI().getPath().startsWith("/api/v1/auth/generate")) {
                logger.info("Bypassing authentication for path: {}", request.getURI().getPath());
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(API_KEY_HEADER)) {
                logger.warn("Missing API key in request: {}", request.getURI());
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String apiKey = request.getHeaders().get(API_KEY_HEADER).get(0);
            if (apiKey == null) {
                logger.warn("API key is null in request: {}", request.getURI());
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            return this.webClient.get()
                .uri("http://localhost:8083/validate?key=" + apiKey)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    logger.info("API Key validation response: {}", response);
                    if (apiKey.equals(response)) {
                        return chain.filter(exchange);
                    }
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                })
                .onErrorResume(throwable -> {
                    logger.error("Error during API key validation", throwable);
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                });
        };
    }
```

- WebClient

`WebClient` is a non-blocking, reactive client for making HTTP requests. It is used in the `AuthenticationFilter` to send a request to an external validation service to check if the provided API key is valid.

- Logging

The `AuthenticationFilter` uses **SLF4J** for logging. This provides a way to log API key validation responses and errors, which helps in monitoring and debugging the application.