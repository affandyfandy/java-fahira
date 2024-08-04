# API Key Management with Interceptor

## Overview

This project includes an API key management system with a custom interceptor that ensures:
- API key validation for specific endpoints.
- Addition of headers containing the username and timestamp in responses.
- Store of the last usage time of each API key.

## Usage

1. Generating API Key: POST to `/api/v1/admin/generate-api-key` to generate a new API key.
2. Access Control: Requests to `/api/v1/admin` do not require an API key. Other endpoints will require a valid API key.
3. Headers: Responses include **user-name** and **timestamp** headers for authenticated requests.

## Implementation

```java
@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Autowired
    private ApiKeyService apiKeyService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        
        // Skip API key validation for URLs starting with "/admin"
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
            return false; // Prevent further processing
        }

        return true; // Continue to the next interceptor or handler
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
```
