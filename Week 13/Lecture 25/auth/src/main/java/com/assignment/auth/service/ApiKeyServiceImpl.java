package com.assignment.auth.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

import com.assignment.auth.data.model.ApiKey;
import com.assignment.auth.data.repository.ApiKeyRepository;

@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyServiceImpl(ApiKeyRepository repository){
        this.apiKeyRepository = repository;
    }

    @Override
    public ApiKey save(ApiKey apiKey) {
        return apiKeyRepository.save(apiKey);
    }

    @Override
    public String validation(String key) {
        ApiKey findKey = apiKeyRepository.findByKey(key);
        if (findKey != null) {
            findKey.setAccessedAt(LocalDate.now());
            return "valid";
        }
        return "invalid";
    }
    
}
