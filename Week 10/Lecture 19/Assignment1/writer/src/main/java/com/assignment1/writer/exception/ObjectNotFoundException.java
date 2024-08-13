package com.assignment1.writer.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends RuntimeException {

    private final HttpStatus errorCode;

    public ObjectNotFoundException(String message) {
        super(message);
        this.errorCode = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
    
}
