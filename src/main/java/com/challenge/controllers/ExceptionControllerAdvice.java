package com.challenge.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.challenge.domain.ErrorResponse;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
	        ErrorResponse error = new ErrorResponse();
	        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        error.setMessage("Please contact Customer Care");
	        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
	    }
}
