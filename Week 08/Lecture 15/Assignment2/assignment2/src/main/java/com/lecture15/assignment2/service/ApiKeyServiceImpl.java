package com.lecture15.assignment2.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lecture15.assignment2.data.entity.ApiKey;
import com.lecture15.assignment2.data.repository.ApiKeyRepository;

@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    @Autowired
    private ApiKeyRepository apiKeyRepository;    

    @Override
    public ApiKey generateAndSaveApiKey() {
        ApiKey newApiKey = new ApiKey();
        newApiKey.setApiKey(UUID.randomUUID().toString());
        return apiKeyRepository.save(newApiKey);
    }

    @Override
    public boolean findApiKey(String apiKey) {
        return apiKeyRepository.existsByApiKey(apiKey);
    }
    
    
}
