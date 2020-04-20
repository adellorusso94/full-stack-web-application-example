package com.example.webapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp.service.PrezzoService;

@RestController
@RequestMapping(value = "api/promo/prezzo")
public class PrezzoController {
	
	@Autowired
	private PrezzoService prezzoService;
	
	// SELEZIONE PREZZO PROMO
	@GetMapping(value = {"/{codArt}/{optCodFid}", "/{codArt}"})
	public double getPricePromo(@PathVariable String codArt, @PathVariable Optional<String> optCodFid) {
		if (optCodFid.isPresent()) {
			System.out.println(String.format("Cerchiamo promo riservata all fidelity %s dell'articolo %s ",optCodFid,codArt));
			return prezzoService.SelByCodArtAndCodFid(codArt, optCodFid.get());
		} else {
			System.out.println(String.format("Cerchiamo Prezzo in promo articolo %s ",codArt));
			return prezzoService.SelPromoByCodArt(codArt);
		}
	}
	
	// SELEZIONE PREZZO PROMO FIDELITY
	@GetMapping(value = {"/fidelity/{codArt}"})
	public double getPricePromoFid(@PathVariable String codArt) {
		System.out.println(String.format("Cerchiamo promo fidelity articolo %s ",codArt));
		return prezzoService.SelPromoByCodArtAndFid(codArt);
	}
	
}