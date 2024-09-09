package com.assignment.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.auth.service.ApiKeyService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final ApiKeyService apiKeyService;

    public AuthController(ApiKeyService apiKeyService){
        this.apiKeyService = apiKeyService;
    }
    
    @GetMapping("/validate")
    public ResponseEntity<String> keyValidation(@RequestHeader("api-key") String key){
        String response = apiKeyService.validation(key);
        if (response.equals("invalid")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        else return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
