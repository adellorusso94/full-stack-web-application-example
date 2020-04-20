package com.example.webapp.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = -5658706227027149951L;
	
	private String messaggio;

	public NotFoundException() { }

	public NotFoundException(String messaggio) {
		super(messaggio);
		this.messaggio = messaggio;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
}
