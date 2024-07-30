package com.lecture15.assignment1.filter;

import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OncePerRequestImpl extends OncePerRequestFilter {

    /**
     * Applies filtering logic to the request and response.
     * This method is called once per request and is responsible for the core filter logic.
     *
     * @param request  the {@link HttpServletRequest} object that contains the request data.
     * @param response the {@link HttpServletResponse} object that contains the response data.
     * @param chain    the {@link FilterChain} used to pass the request and response to the next filter or target resource.
     * @throws ServletException if a servlet-specific error occurs during the request processing.
     * @throws IOException      if an input or output error occurs while processing the request or response.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Passes the request and response to the next entity in the filter chain
        chain.doFilter(request,response);
        
        // Logs a message after the request has been processed
        log.info("doFilter in OncePerRequestImpl");
    }
}