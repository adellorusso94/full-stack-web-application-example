package com.example.webapp.service;

import java.util.List;

import com.example.webapp.model.Utenti;

public interface UtentiService {
	
	public List<Utenti> getAll();
	public Utenti getOne(String username);
	public void save(Utenti utente);
	public void delete(Utenti utente);
	
}
