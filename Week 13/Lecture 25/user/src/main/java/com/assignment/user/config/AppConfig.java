package com.assignment.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
// import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Configuration
public class AppConfig {
    // @LoadBalanced
	@Bean
	public RestTemplate restTemplateBean() {
		return new RestTemplate();
	}

    @Bean
	public ObjectMapper objectMapperBean() {
		return new JsonMapper()
				.enable(SerializationFeature.INDENT_OUTPUT);
	}
}
