package com.example.webapp.exception;

public class DuplicateException extends Exception {

	private static final long serialVersionUID = 7074432867393491080L;
	
	private String messaggio;
	
	public DuplicateException() { }
	
	public DuplicateException(String messaggio) {
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
