package com.example.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Articoli;
import com.example.webapp.repository.ArticoliRepository;

@Service
@Transactional(readOnly = true)
public class ArticoliServiceImpl implements ArticoliService {

	@Autowired
	private ArticoliRepository articoliRepository;
	
	@Override
	public Iterable<Articoli> getAll() {
		return articoliRepository.findAll();
	}

	@Override
	@Cacheable(value = "articolicache", sync = true)
	public List<Articoli> getList(String descrizione) {
		return articoliRepository.SelByDescrizioneLike(descrizione);
	}

	@Override
	public List<Articoli> getList(String descrizione, Pageable pageable) {
		return articoliRepository.findByDescrizioneLike(descrizione, pageable);
	}

	@Override
	@Cacheable(value = "articolo", key = "#codArt", sync = true)
	public Articoli getOne(String codArt) {
		return articoliRepository.findByCodArt(codArt);
	}

	@Override
	@Transactional
	@Caching(evict = {
			@CacheEvict(cacheNames = "articolicache", allEntries = true),
			@CacheEvict(cacheNames = "articolo", key = "#articolo.codArt")
	})
	public void delete(Articoli articolo) {
		articoliRepository.delete(articolo);
	}

	@Override
	@Transactional
	@Caching(evict = {
			@CacheEvict(cacheNames = "articolicache", allEntries = true),
			@CacheEvict(cacheNames = "articolo", key = "#articolo.codArt")
	})
	public void save(Articoli articolo) {
		articoliRepository.save(articolo);
	}

}
