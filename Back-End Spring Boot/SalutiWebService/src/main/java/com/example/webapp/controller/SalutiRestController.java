package com.example.webapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class SalutiRestController {
	
	@GetMapping("/saluti")
	public String getGreetings() {
		return "Saluti, sono il tuo primo web service.";
	}
	
	@GetMapping("/saluti/{name}")
	public String getGreetingsWithName(@PathVariable String name) {
		if (name.equals("Antonio"))
			throw new RuntimeException("L'utente Antonio è disabilitato.");
		return "\"Saluti " + name + ", hai usato il tuo primo web service.\"";
	}
	
}
