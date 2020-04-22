package com.example.webapp.security;

import java.util.List;

public class Utenti {
	
	private String id;
	private String username;
	private String password;
	private String attivo;
	private List<String> ruoli;
	
	public Utenti() {
		
	}

	public Utenti(String id, String username, String password, String attivo, List<String> ruoli) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.attivo = attivo;
		this.ruoli = ruoli;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAttivo() {
		return attivo;
	}

	public void setAttivo(String attivo) {
		this.attivo = attivo;
	}

	public List<String> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<String> ruoli) {
		this.ruoli = ruoli;
	}
	
}
