package com.example.webapp.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp.appconf.AppConfig;
import com.example.webapp.entity.DettListini;
import com.example.webapp.exception.BindingException;
import com.example.webapp.exception.DuplicateException;
import com.example.webapp.service.PrezziService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class PrezziController {

	private static final Logger logger = LoggerFactory.getLogger(PrezziController.class);
	
	@Autowired
	private PrezziService prezziService;
	
	@Autowired
	private ResourceBundleMessageSource errMessage;
	
	@Autowired
	private AppConfig appConfig;

	// Ricerca prezzo di un articolo (NO AUTH)
	@ApiOperation(value = "Ricerca prezzo per codice articolo (NOAUTH)", notes = "Restituisce un oggetto in formato Double", response = double.class, produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Prezzo Trovato"),
			@ApiResponse(code = 404, message = "Prezzo Non Trovato") })
	@GetMapping(value = { "/noauth/{codArt}", "/noauth/{codArt}/{optIdList}" })
	@RefreshScope
	public double getPrezzoNoAuth(@PathVariable String codArt, @PathVariable Optional<String> optIdList) {
		logger.info("****** Otteniamo il prezzo dell'articolo con barcode " + codArt + " *******");
		String idList = (optIdList.isPresent()) ? optIdList.get() : appConfig.getListino();
		DettListini prezzo = prezziService.getOne(codArt, idList);
		if (prezzo == null) {
			logger.warn("L'articolo con codice " + codArt + " non è stato trovato!");
			return 0;
		} else {
			return prezzo.getPrezzo();
		}
	}

	// Ricerca prezzo di un articolo
	@ApiOperation(value = "Ricerca prezzo per codice articolo", notes = "Restituisce un oggetto in formato Double", response = double.class, produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Prezzo Trovato"),
			@ApiResponse(code = 404, message = "Prezzo Non Trovato") })
	@GetMapping(value = { "/{codArt}", "/{codArt}/{optIdList}" })
	@RefreshScope
	public double getPrezzo(@PathVariable String codArt, @PathVariable Optional<String> optIdList) {
		logger.info("****** Otteniamo il prezzo dell'articolo con barcode " + codArt + " *******");
		String idList = (optIdList.isPresent()) ? optIdList.get() : appConfig.getListino();
		DettListini prezzo = prezziService.getOne(codArt, idList);
		if (prezzo == null) {
			logger.warn("L'articolo con codice " + codArt + " non è stato trovato!");
			return 0;
		} else {
			return prezzo.getPrezzo();
		}
	}

	// Inserimento prezzo di un articolo
	@ApiOperation(value = "Inserisci il prezzo", notes = "Restituisce un messaggio di stato in formato JSON", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Prezzo Inserito"),
			@ApiResponse(code = 400, message = "Dati Prezzo in Formato Errato"),
			@ApiResponse(code = 406, message = "Prezzo Già Presente") })
	@PostMapping(value = "/inserisci", produces = "application/json")
	public ResponseEntity<?> createListino(@Valid @RequestBody DettListini dettListino, BindingResult bindingResult)
			throws BindingException, DuplicateException {
		logger.info("****** Inseriamo il prezzo dell'articolo con codice " + dettListino.getCodArt() + " *******");
		if (bindingResult.hasErrors()) {
			String errMsg = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			logger.warn(errMsg);
			throw new BindingException(errMsg);
		} else {
			prezziService.save(dettListino);
			HttpHeaders headers = new HttpHeaders();
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode responseNode = mapper.createObjectNode();
			responseNode.put("code", HttpStatus.OK.toString());
			responseNode.put("message", "Inserimento Prezzo " + dettListino.getPrezzo() + " Eseguito Con Successo");
			return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
		}
	}

	// Eliminazione prezzo di un articolo
	@ApiOperation(value = "Elimina il prezzo", notes = "Restituisce un messaggio di stato in formato JSON", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Prezzo Eliminato"),
			@ApiResponse(code = 404, message = "Prezzo Non Trovato") })
	@DeleteMapping(value = "/elimina/{codArt}/{idList}", produces = "application/json")
	public ResponseEntity<?> deletePrezzo(@PathVariable String codArt, @PathVariable String idList) {
		logger.info("****** Eliminiamo il prezzo dell'articolo con codice " + codArt + " *******");
		prezziService.delete(codArt, idList);
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Prezzo Eseguita Con Successo");
		return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
	}

}
