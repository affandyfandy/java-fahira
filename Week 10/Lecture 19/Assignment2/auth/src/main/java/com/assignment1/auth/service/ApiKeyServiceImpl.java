package com.assignment1.auth.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment1.auth.data.model.ApiKey;
import com.assignment1.auth.data.repository.ApiKeyRepository;


@Service
public class ApiKeyServiceImpl implements ApiKeyService{

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyServiceImpl(ApiKeyRepository apiKeyRepository){
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    @Transactional
    public ApiKey generateAndSaveApiKey(){
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
    @Transactional
    public boolean findApiKey(String apiKey) {
        return apiKeyRepository.existsByApiKey(apiKey);
    }

    @Override
    @Transactional
    public String getUsernameForApiKey(String apiKey) {
        return apiKeyRepository.findUsernameByApiKey(apiKey);
    }

    // @Override
    // @Transactional
    // public void updateLastUsage(String apiKey) {
    //     ApiKey findApi = apiKeyRepository.findByApiKey(apiKey);
    //     findApi.setLastUsed(LocalDateTime.now());
    //     apiKeyRepository.save(findApi);
    // }
    
}
