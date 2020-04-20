package com.example.webapp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<ErrorResponse> exceptionNotFoundHandler(Exception ex) {
		ErrorResponse errore = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<ErrorResponse>(errore, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BindingException.class)
	public final ResponseEntity<ErrorResponse> exceptionBindingHandler(Exception ex) {
		ErrorResponse errore = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<ErrorResponse>(errore, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateException.class)
	public final ResponseEntity<ErrorResponse> exceptionDUplicateHandler(Exception ex) {
		ErrorResponse errore = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
		return new ResponseEntity<ErrorResponse>(errore, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
	}
	
}
