package com.example.webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.webapp.model.Utenti;

public interface UtentiRepository extends MongoRepository<Utenti, String> {
	public Utenti findByUsername(String Username);
}
