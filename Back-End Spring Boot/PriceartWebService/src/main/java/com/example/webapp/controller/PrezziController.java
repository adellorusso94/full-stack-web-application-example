package com.example.webapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp.appconf.AppConfig;
import com.example.webapp.entity.DettListini;
import com.example.webapp.service.PrezziService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api/prezzi")
public class PrezziController {

	@Autowired
	private PrezziService prezziService;

	@Autowired
	private AppConfig appConfig;

	// Ricerca prezzo di un articolo (NO AUTH)
	@GetMapping(value = { "/noauth/{codArt}", "/noauth/{codArt}/{optIdList}" })
	public double getPrezzoNoAuth(@PathVariable String codArt, @PathVariable Optional<String> optIdList) {
		String idList = (optIdList.isPresent()) ? optIdList.get() : appConfig.getListino();
		DettListini prezzo = prezziService.getPrezzo(codArt, idList);
		if (prezzo == null) {
			System.out.println("L'articolo con codice " + codArt + " non è stato trovato!");
			return 0;
		} else {
			return prezzo.getPrezzo();
		}
	}

	// Ricerca prezzo di un articolo
	@GetMapping(value = { "/{codArt}", "/{codArt}/{optIdList}" })
	public double getPrezzo(@PathVariable String codArt, @PathVariable Optional<String> optIdList) {
		String idList = (optIdList.isPresent()) ? optIdList.get() : appConfig.getListino();
		DettListini prezzo = prezziService.getPrezzo(codArt, idList);
		if (prezzo == null) {
			System.out.println("L'articolo con codice " + codArt + " non è stato trovato!");
			return 0;
		} else {
			return prezzo.getPrezzo();
		}
	}

	// Eliminazione prezzo di un articolo
	@DeleteMapping(value = "/elimina/{codArt}/{idList}", produces = "application/json")
	public ResponseEntity<?> deletePrezzo(@PathVariable String codArt, @PathVariable String idList) {
		prezziService.deletePrezzo(codArt, idList);
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Prezzo Eseguita Con Successo");
		return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
	}

}
