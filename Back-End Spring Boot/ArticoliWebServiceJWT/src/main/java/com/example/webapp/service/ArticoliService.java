package com.example.webapp.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.webapp.entity.Articoli;

public interface ArticoliService {
	
	public Iterable<Articoli> getAll();
	public List<Articoli> getList(String descrizione);
	public List<Articoli> getList(String descrizione, Pageable pageable);
	public Articoli getOne(String codArt);
	public void delete(Articoli articolo);
	public void save(Articoli articolo);
	
}
