package com.example.webapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp.service.PrezzoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "api/promo/prezzo")
public class PrezzoController {
	
	@Autowired
	private PrezzoService prezzoService;
	
	// SELEZIONE PREZZO PROMO
	@ApiOperation(value = "Ricerca prezzo promozione per codice articolo", notes = "Restituisce un oggetto in formato Double", response = double.class, produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Prezzo Trovato"),
			@ApiResponse(code = 404, message = "Prezzo Non Trovato") })
	@GetMapping(value = {"/{codArt}/{optCodFid}", "/{codArt}"})
	public double getPricePromo(@PathVariable String codArt, @PathVariable Optional<String> optCodFid) {
		if (optCodFid.isPresent()) {
			System.out.println(String.format("Cerchiamo promo riservata all fidelity %s dell'articolo %s ",optCodFid,codArt));
			return prezzoService.getOneByCodArtAndCodFid(codArt, optCodFid.get());
		} else {
			System.out.println(String.format("Cerchiamo Prezzo in promo articolo %s ",codArt));
			return prezzoService.getOneByCodArt(codArt);
		}
	}
	
	// SELEZIONE PREZZO PROMO FIDELITY
	@ApiOperation(value = "Ricerca prezzo promozione fidelity per codice articolo", notes = "Restituisce un oggetto in formato Double", response = double.class, produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Prezzo Trovato"),
			@ApiResponse(code = 404, message = "Prezzo Non Trovato") })
	@GetMapping(value = {"/fidelity/{codArt}"})
	public double getPricePromoFid(@PathVariable String codArt) {
		System.out.println(String.format("Cerchiamo promo fidelity articolo %s ",codArt));
		return prezzoService.getOneByCodArtAndFid(codArt);
	}
	
}