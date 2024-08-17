package com.assignment.auth.service;

import com.assignment.auth.data.model.ApiKey;

public interface ApiKeyService {
    
    ApiKey save(ApiKey apiKey);
    String validation(String key);
}
