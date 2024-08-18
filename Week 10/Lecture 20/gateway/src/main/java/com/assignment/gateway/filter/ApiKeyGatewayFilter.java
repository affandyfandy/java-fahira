package com.assignment.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ApiKeyGatewayFilter extends AbstractGatewayFilterFactory<ApiKeyGatewayFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(ApiKeyGatewayFilter.class);
    
    private RestTemplate restTemplate;

    private static final String API_KEY = "api-key";

    public ApiKeyGatewayFilter(RestTemplate restTemplate){
        super(Config.class);
        this.restTemplate = restTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders header = exchange.getRequest().getHeaders();
            String key = header.getFirst(API_KEY);

            logger.info("Received request for URI: {}", exchange.getRequest().getURI());
            
            if (!header.containsKey(API_KEY) || key == null){
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            try {
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.set(API_KEY, key);

                ResponseEntity<String> response = restTemplate.getForEntity(
                        "http://localhost:8083/api/v1/auth/validate", String.class, requestHeaders);

                if ("invalid".equals(response.getBody())) {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }
            } catch (HttpClientErrorException ex) {
                logger.error("Error during API key validation: {}", ex.getMessage());
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    public static class Config{
        // Put the configuration properties ...
    }

}