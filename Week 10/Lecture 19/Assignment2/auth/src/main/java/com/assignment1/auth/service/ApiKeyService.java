package com.assignment1.auth.service;

import com.assignment1.auth.data.model.ApiKey;

public interface ApiKeyService {
    
    ApiKey generateAndSaveApiKey();
    boolean findApiKey(String apiKey);
    String getUsernameForApiKey(String apiKey);
    // void updateLastUsage(String apiKey);
}
