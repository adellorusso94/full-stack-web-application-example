package com.example.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Articoli;
import com.example.webapp.repository.ArticoliRepository;

@Service
public class ArticoliServiceImpl implements ArticoliService {

	@Autowired
	private ArticoliRepository articoliRepository;
	
	@Override
	@Transactional
	public Iterable<Articoli> getAll() {
		return articoliRepository.findAll();
	}

	@Override
	@Transactional
	public List<Articoli> getList(String descrizione) {
		return articoliRepository.SelByDescrizioneLike(descrizione);
	}

	@Override
	@Transactional
	public List<Articoli> getList(String descrizione, Pageable pageable) {
		return articoliRepository.findByDescrizioneLike(descrizione, pageable);
	}

	@Override
	@Transactional
	public Articoli getOne(String codArt) {
		return articoliRepository.findByCodArt(codArt);
	}

	@Override
	@Transactional
	public void delete(Articoli articolo) {
		articoliRepository.delete(articolo);
	}

	@Override
	@Transactional
	public void save(Articoli articolo) {
		articoliRepository.save(articolo);
	}

}
