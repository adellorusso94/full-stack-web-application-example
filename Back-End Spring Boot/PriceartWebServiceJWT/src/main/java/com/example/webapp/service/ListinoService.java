package com.example.webapp.service;

import java.util.Optional;

import com.example.webapp.entity.Listini;

public interface ListinoService {
	
	public Optional<Listini> getOne(String id);
	public void save(Listini listino);
	public void delete(Listini listino);
	
}
