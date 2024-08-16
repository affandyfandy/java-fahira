package com.assignment1.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.assignment1.auth.data.model.ApiKey;
import com.assignment1.auth.service.ApiKeyService;

@RestController
@RequestMapping("/api/v1/auth")
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService){
        this.apiKeyService = apiKeyService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ApiKey> generateApiKey() {
        ApiKey newApiKey = apiKeyService.generateAndSaveApiKey();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(newApiKey);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateApi(@RequestParam(name = "key") String id){
        boolean response = apiKeyService.findApiKey(id);
        if (response) return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid key!");
    }

}
