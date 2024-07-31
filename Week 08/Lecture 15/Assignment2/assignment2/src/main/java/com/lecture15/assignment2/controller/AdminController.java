package com.lecture15.assignment2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lecture15.assignment2.data.entity.ApiKey;
import com.lecture15.assignment2.service.ApiKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final ApiKeyService apiKeyService;

    public AdminController(ApiKeyService apiKeyService){
        this.apiKeyService = apiKeyService;
    }
    
    @PostMapping("/generate-api-key")
    public ResponseEntity<ApiKey> generateApiKey() {
        ApiKey newApiKey = apiKeyService.generateAndSaveApiKey();
        return ResponseEntity.ok(newApiKey);
    }

}
