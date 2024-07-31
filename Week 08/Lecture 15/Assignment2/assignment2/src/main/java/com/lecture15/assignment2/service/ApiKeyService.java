package com.lecture15.assignment2.service;

import com.lecture15.assignment2.data.entity.ApiKey;

public interface ApiKeyService {
    ApiKey generateAndSaveApiKey();
    boolean findApiKey(String apiKey);
}
