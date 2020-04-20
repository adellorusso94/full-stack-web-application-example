package com.example.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.model.Utenti;
import com.example.webapp.repository.UtentiRepository;

@Service
@Transactional(readOnly = true)
public class UtentiServiceImpl implements UtentiService{
	
	@Autowired
	UtentiRepository utentiRepository;
	
	@Override
	public List<Utenti> getAll() {
		return utentiRepository.findAll();
	}

	@Override
	public Utenti getOne(String username) {
		return utentiRepository.findByUsername(username);
	}

	@Override
	public void save(Utenti utente) {
		utentiRepository.save(utente);
	}

	@Override
	public void delete(Utenti utente) {
		utentiRepository.delete(utente);
	}

}
