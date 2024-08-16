package com.assignment1.api_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final String API_KEY_HEADER = "api-key";
    private WebClient webClient;

	public AuthenticationFilter(WebClient.Builder webClientBuilder) {
		super(Config.class);
		this.webClient = webClientBuilder.build();
	}

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

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    public static class Config{}
}
