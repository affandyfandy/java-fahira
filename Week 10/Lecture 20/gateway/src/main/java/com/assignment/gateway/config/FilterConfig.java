package com.assignment.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FilterConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
	public RouteLocator routerBuilder(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route("product-service",r -> r.path("/api/v1/product/**")
                        .filters(f -> f.addRequestHeader("api-key", "api-key-value"))
                        .uri("http://localhost:8081"))
                .route("customer-service", r -> r.path("/api/v1/customer/**")
                        .filters(f -> f.addRequestHeader("api-key", "api-key-value"))
                        .uri("http://localhost:8082"))
                .build();
	}
}