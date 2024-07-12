package com.lecture10.assignment1.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException{
	public EmployeeNotFoundException(String x) {
		super(x);
	}
}
