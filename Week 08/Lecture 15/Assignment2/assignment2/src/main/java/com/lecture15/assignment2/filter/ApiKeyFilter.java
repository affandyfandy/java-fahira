package com.lecture15.assignment2.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.lecture15.assignment2.service.ApiKeyService;
import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    private final ApiKeyService apiKeyService;

    public ApiKeyFilter(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        if (requestURI.startsWith("/api/v1/admin/generate-api-key")) {
            chain.doFilter(request, response);
            return;
        }

        String apiKey = httpRequest.getHeader("api-key");

        if (apiKey == null || !apiKeyService.findApiKey(apiKey)) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().write("Invalid or missing API key");
            return;
        }

        chain.doFilter(request, response);
    }
}