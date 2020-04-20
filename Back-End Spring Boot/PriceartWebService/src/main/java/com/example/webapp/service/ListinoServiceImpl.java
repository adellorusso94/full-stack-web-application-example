package com.example.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public Optional<Listini> getOne(String id) {
		return listinoRepository.findById(id);
	}

	@Override
	public void save(Listini listino) {
		listinoRepository.save(listino);
	}

	@Override
	public void delete(Listini listino) {
		listinoRepository.delete(listino);
	}

}
