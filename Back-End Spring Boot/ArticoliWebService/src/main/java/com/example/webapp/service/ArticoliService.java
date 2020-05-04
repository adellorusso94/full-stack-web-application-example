package com.example.webapp.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.webapp.entity.Articoli;

public interface ArticoliService {
	
	public Iterable<Articoli> getAll();
	public List<Articoli> getList(String descrizione, String header);
	public List<Articoli> getList(String descrizione, Pageable pageable, String header);
	public Articoli getOne(String codArt, String header);
	public void delete(Articoli articolo);
	public void save(Articoli articolo);
	
}
