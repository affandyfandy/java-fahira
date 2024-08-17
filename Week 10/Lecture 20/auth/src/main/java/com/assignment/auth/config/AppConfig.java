package com.assignment.auth.config;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assignment.auth.data.model.ApiKey;
import com.assignment.auth.data.repository.ApiKeyRepository;

@Configuration
public class AppConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;
    
    @Bean
    public CommandLineRunner dataLoader(ApiKeyRepository apiKeyRepository) {
        return args -> {
            if ("create".equals(ddlAuto)){
                ApiKey newApiKey = new ApiKey();
                newApiKey.setKeyId(UUID.randomUUID().toString());
                newApiKey.setAccessedAt(LocalDate.now());
    
                apiKeyRepository.save(newApiKey);
            }
        };
    }
}
