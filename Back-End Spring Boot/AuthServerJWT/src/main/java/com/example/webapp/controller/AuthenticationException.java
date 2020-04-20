package com.example.webapp.controller;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 2119464857832346710L;

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
