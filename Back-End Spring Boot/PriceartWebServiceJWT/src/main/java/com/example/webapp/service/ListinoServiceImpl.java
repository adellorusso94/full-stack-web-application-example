package com.example.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Listini;
import com.example.webapp.repository.ListinoRepository;

@Service
@Transactional
public class ListinoServiceImpl implements ListinoService {
	
	@Autowired
	private ListinoRepository listinoRepository;
	
	@Override
	@Cacheable(value = "cachelistino", key = "#id", sync = true)
	public Optional<Listini> getOne(String id) {
		return listinoRepository.findById(id);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = "cachelistino", key = "#listino.id")
	})
	public void save(Listini listino) {
		listinoRepository.save(listino);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = "cachelistino", key = "#listino.id")
	})
	public void delete(Listini listino) {
		listinoRepository.delete(listino);
	}

}
