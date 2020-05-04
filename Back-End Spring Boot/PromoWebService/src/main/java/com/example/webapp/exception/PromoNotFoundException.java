package com.example.webapp.exception;

public class PromoNotFoundException extends Exception {

	private static final long serialVersionUID = 3547363154497323313L;
	
	private String messaggio;

	public PromoNotFoundException() { }

	public PromoNotFoundException(String messaggio) {
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
