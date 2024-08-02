package com.lecture15.assignment3.service;

import com.lecture15.assignment3.data.entity.ApiKey;

public interface ApiKeyService {
    ApiKey generateAndSaveApiKey();
    boolean findApiKey(String apiKey);
    String getUsernameForApiKey(String apiKey);
    void updateLastUsage(String apiKey);
}
