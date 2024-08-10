package com.midterm.group4.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class GlobalExceptionHandlerTest {

    @Mock
    private ObjectNotFoundException objectNotFoundException;

    @Mock
    private InvalidInputException invalidInputException;

    @Mock
    private InvalidFileContentException invalidFileContentException;

    private GlobalExceptionHandler handler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Test 1: Handle ObjectNotFoundException should return Not Found")
    public void handleObjectNotFoundException_shouldReturnNotFound() {
        String errorMessage = "Object not found";
        when(objectNotFoundException.getMessage()).thenReturn(errorMessage);

        ResponseEntity<ErrorResponse> response = handler.handleObjectNotFoundException(objectNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    @DisplayName("Test 2: Handle InvalidInputException should return Bad Request")
    public void handleInvalidInputException_shouldReturnBadRequest() {
        String errorMessage = "Invalid input!";
        when(invalidInputException.getMessage()).thenReturn(errorMessage);

        ResponseEntity<ErrorResponse> response = handler.handleInvalidInputException(invalidInputException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    @DisplayName("Test 3: Handle InvalidFileContentException should return Bad Request")
    public void handleInvalidFileContentException_shouldReturnBadRequest() {
        String errorMessage = "Invalid file content";
        when(invalidFileContentException.getMessage()).thenReturn(errorMessage);

        ResponseEntity<ErrorResponse> response = handler.handleInvalidFileContentException(invalidFileContentException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    @DisplayName("Test 4: Handle Generic Exception")
    public void handleGenericException_shouldReturnInternalServerError() {
        String errorMessage = "Something went wrong";
        Exception error = new Exception(errorMessage);
        ResponseEntity<ErrorResponse> response = handler.handleGenericException(error);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

}
