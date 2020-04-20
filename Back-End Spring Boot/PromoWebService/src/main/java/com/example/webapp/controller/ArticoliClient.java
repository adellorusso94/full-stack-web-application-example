package com.example.webapp.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.webapp.entity.Articoli;

@FeignClient(name="Products", url="localhost:5051")
public interface ArticoliClient {
	
	@GetMapping(value = "api/articoli/noauth/cerca/codice/{codArt}")
    public Articoli getArticolo(@PathVariable String codArt);
	
}
