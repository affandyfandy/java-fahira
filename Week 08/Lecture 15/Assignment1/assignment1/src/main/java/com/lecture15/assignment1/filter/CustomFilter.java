package com.lecture15.assignment1.filter;

import java.io.IOException;

import jakarta.servlet.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFilter implements Filter {

      /**
     * Applies filtering logic to the request and response.
     * This method is invoked for every request that matches the URL patterns defined in the filter configuration.
     *
     * @param request  the {@link ServletRequest} object that contains the request data.
     * @param response the {@link ServletResponse} object that contains the response data.
     * @param chain    the {@link FilterChain} used to pass the request and response to the next filter or target resource.
     * @throws IOException      if an input or output error occurs while processing the request or response.
     * @throws ServletException if a servlet-specific error occurs during the request processing.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Passes the request and response to the next entity in the filter chain
        chain.doFilter(request, response);

        // Logs a message after the request has been processed
        log.info("doFilter in CustomFilter");
    }
}