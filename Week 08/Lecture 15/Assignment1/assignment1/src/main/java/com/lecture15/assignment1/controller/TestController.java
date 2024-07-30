package com.lecture15.assignment1.controller;

import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
public class TestController {
 
    /**
     * Handles GET requests to "/will-redirect".
     * This endpoint performs a redirect to the "/redirected" URL.
     * 
     * @param response the {@link HttpServletResponse} object used to send the redirect response.
     * @throws IOException if an input or output error occurs while sending the redirect.
     */
    @GetMapping("/will-redirect")
    public void willRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/redirected");
    }

    /**
     * Handles GET requests to "/redirected".
     * This endpoint returns a simple string "redirected" to indicate that the redirect was successful.
     * 
     *
     * @return a string indicating the result of the redirect.
     */
    @GetMapping("/redirected")
    public String redirected() {
        return "redirected";
    }

    /**
     * Handles GET requests to "/will-forward". 
     * This endpoint performs a forward to the "/forwarded" URL.
     *
     * @param request the {@link HttpServletRequest} object used to forward the request.
     * @param response the {@link HttpServletResponse} object used to forward the response.
     * @throws IOException if an input or output error occurs while forwarding the request.
     * @throws ServletException if the request could not be forwarded due to a servlet-specific issue.
     */
    @GetMapping("/will-forward")
    public void willForward(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/forwarded").forward(request, response);
    }


    /**
     * Handles GET requests to "/forwarded".
     * This endpoint returns a simple string "forwarded" to indicate that the forward was successful.
     *
     * @return a string indicating the result of the forward.
     */
    @GetMapping("/forwarded")
    public String forwarded() {
        return "forwarded";
    }
}