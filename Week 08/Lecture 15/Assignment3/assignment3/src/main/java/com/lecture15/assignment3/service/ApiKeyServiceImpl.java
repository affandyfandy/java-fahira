package com.lecture15.assignment3.service;

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lecture15.assignment3.data.entity.ApiKey;
import com.lecture15.assignment3.data.repository.ApiKeyRepository;

@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    @Autowired
    private ApiKeyRepository apiKeyRepository;    

    @Override
    public ApiKey generateAndSaveApiKey() {
        ApiKey newApiKey = new ApiKey();
        String newUUID = UUID.randomUUID().toString();
        newApiKey.setApiKey(newUUID);
        newApiKey.setUsername(generateUsername(newUUID));
        return apiKeyRepository.save(newApiKey);
    }

    private String generateUsername(String apiKey) {
        return "user_" + apiKey.hashCode();
    }

    @Override
    public boolean findApiKey(String apiKey) {
        return apiKeyRepository.existsByApiKey(apiKey);
    }

    @Override
    public String getUsernameForApiKey(String apiKey) {
        return apiKeyRepository.findUsernameByApiKey(apiKey);
    }

    @Override
    public void updateLastUsage(String apiKey) {
        ApiKey findApi = apiKeyRepository.findByApiKey(apiKey);
        findApi.setLastUsed(LocalDateTime.now());
    }
}
