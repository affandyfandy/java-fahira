package com.assignment.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyGatewayFilter extends AbstractGatewayFilterFactory<ApiKeyGatewayFilter.Config> {
    
    private WebClient webClient;

    public ApiKeyGatewayFilter(WebClient.Builder webClientBuilder){
        super(Config.class);
        this.webClient = webClientBuilder.build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders header = exchange.getRequest().getHeaders();
            String key = header.getFirst("api-key");
            
            if (!header.containsKey("api-key") || key == null){
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            System.out.println("JEYYYY " + key);

            return webClient.get()
                .uri("http://localhost:8083/api/v1/auth/validate?key=" + key)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    if ("invalid".equals(response)){
                        return onError(exchange, HttpStatus.UNAUTHORIZED);
                    }
                    return chain.filter(exchange);
                })
                .onErrorResume(throwable -> {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                });
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