package com.example.webapp.exception;

import java.util.Date;

public class ErrorResponse {
	private Date data;
	private int codice;
	private String messaggio;
	
	public ErrorResponse(int codice, String messaggio) {
		this.data = new Date();
		this.codice = codice;
		this.messaggio = messaggio;
	}

	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public int getCodice() {
		return codice;
	}
	
	public void setCodice(int codice) {
		this.codice = codice;
	}
	
	public String getMessaggio() {
		return messaggio;
	}
	
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
}
