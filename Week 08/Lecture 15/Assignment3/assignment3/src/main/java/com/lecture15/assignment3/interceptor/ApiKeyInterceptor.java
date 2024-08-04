package com.lecture15.assignment3.interceptor;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.lecture15.assignment3.service.ApiKeyService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Autowired
    private ApiKeyService apiKeyService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        
        if (requestURI.startsWith("/api/v1/admin")) {
            return true;
        }

        String apiKey = request.getHeader("api-key");

        if (apiKey != null && apiKeyService.findApiKey(apiKey)) {
            String username = apiKeyService.getUsernameForApiKey(apiKey);
            if (username != null) {
                request.setAttribute("username", username);
            }
            apiKeyService.updateLastUsage(apiKey);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Invalid or missing API key");
            return false;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String username = (String) request.getAttribute("username");
        if (username != null) {
            response.setHeader("user-name", username);
        }
        response.setHeader("timestamp", LocalDateTime.now().toString());
    }
}