package com.example.webapp.exception;

public class BindingException extends Exception {

	private static final long serialVersionUID = -1219393780754322533L;
	
	private String messaggio;
	
	public BindingException() { }
	
	public BindingException(String messaggio) {
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
