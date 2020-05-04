package com.example.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp.entity.Articoli;
import com.example.webapp.entity.Barcode;
import com.example.webapp.exception.BindingException;
import com.example.webapp.exception.DuplicateException;
import com.example.webapp.exception.NotFoundException;
import com.example.webapp.service.ArticoliService;
import com.example.webapp.service.BarcodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
public class ArticoliController {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticoliController.class);
	
	@Autowired
	private ArticoliService articoliService;

	@Autowired
	private BarcodeService barcodeService;

	@Autowired
	private ResourceBundleMessageSource errMessage;

	// Test di connessione
	@ApiOperation(value = "Test di connessione", notes = "Restituisce un messaggio di stato in formato JSON", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Connessione Effettuata") })
	@GetMapping(value = "/test")
	public ResponseEntity<?> testConnex() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Test Connessione OK");
		return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
	}

	// Ricerca articolo per barcode
	@ApiOperation(value = "Ricerca l'articolo per codice a barre", notes = "Restituisce i dati dell'articolo in formato JSON", response = Articoli.class, produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Articolo Trovato"),
			@ApiResponse(code = 404, message = "Articolo Non Trovato") })
	@GetMapping(value = "/cerca/ean/{barcode}", produces = "application/json")
	public ResponseEntity<Articoli> getArticoloByEan(@PathVariable String barcode, HttpServletRequest httpRequest)
			throws NotFoundException {
		logger.info("****** Otteniamo l'articolo con barcode " + barcode + " *******");
		String AuthHeader = httpRequest.getHeader("Authorization");
		Barcode ean = barcodeService.getOne(barcode, AuthHeader);
		if (ean == null) {
			String errMsg = "Il barcode " + barcode + " non è stato trovato!";
			logger.warn(errMsg);
			throw new NotFoundException(errMsg);
		} else {
			return new ResponseEntity<Articoli>(ean.getArticolo(), HttpStatus.OK);
		}
	}

	// Ricerca articolo per codice (NO AUTH)
	@ApiOperation(value = "Ricerca l'articolo per codice articolo (NO AUTH)", notes = "Restituisce i dati dell'articolo in formato JSON", response = Articoli.class, produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Articolo Trovato"),
			@ApiResponse(code = 404, message = "Articolo Non Trovato") })
	@GetMapping(value = "/noauth/cerca/codice/{codArt}", produces = "application/json")
	public ResponseEntity<Articoli> getArticoloByCodArtNoAuth(@PathVariable String codArt) throws NotFoundException {
		logger.info("****** Otteniamo l'articolo con codice " + codArt + " *******");
		Articoli articolo = articoliService.getOne(codArt, "");
		if (articolo == null) {
			String errMsg = "L'articolo con codice " + codArt + " non è stato trovato!";
			logger.warn(errMsg);
			throw new NotFoundException(errMsg);
		} else {
			return new ResponseEntity<Articoli>(articolo, HttpStatus.OK);
		}
	}

	// Ricerca articolo per codice
	@ApiOperation(value = "Ricerca l'articolo per codice articolo", notes = "Restituisce i dati dell'articolo in formato JSON", response = Articoli.class, produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Articolo Trovato"),
			@ApiResponse(code = 404, message = "Articolo Non Trovato") })
	@GetMapping(value = "/cerca/codice/{codArt}", produces = "application/json")
	public ResponseEntity<Articoli> getArticoloByCodArt(@PathVariable String codArt, HttpServletRequest httpRequest)
			throws NotFoundException {
		logger.info("****** Otteniamo l'articolo con codice " + codArt + " *******");
		String AuthHeader = httpRequest.getHeader("Authorization");
		Articoli articolo = articoliService.getOne(codArt, AuthHeader);
		if (articolo == null) {
			String errMsg = "L'articolo con codice " + codArt + " non è stato trovato!";
			logger.warn(errMsg);
			throw new NotFoundException(errMsg);
		} else {
			return new ResponseEntity<Articoli>(articolo, HttpStatus.OK);
		}
	}

	// Ricerca articolo per descrizione
	@ApiOperation(value = "Ricerca articoli per descrizione", notes = "Restituisce i dati degli articoli in formato JSON", response = List.class, produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Articoli Trovati"),
			@ApiResponse(code = 404, message = "Articoli Non Trovati") })
	@GetMapping(value = "/cerca/descrizione/{filter}", produces = "application/json")
	public ResponseEntity<List<Articoli>> getArticoloByDescrizione(@PathVariable String filter,
			HttpServletRequest httpRequest) throws NotFoundException {
		logger.info("****** Otteniamo gli articoli con Descrizione: " + filter + " *******");
		String AuthHeader = httpRequest.getHeader("Authorization");
		List<Articoli> lista_articoli = articoliService.getList(filter.toUpperCase() + "%", AuthHeader);
		if (lista_articoli.isEmpty()) {
			String errMsg = "Non è stato trovato alcun articolo avente descrizione " + filter + ".";
			logger.warn(errMsg);
			throw new NotFoundException(errMsg);
		} else {
			return new ResponseEntity<List<Articoli>>(lista_articoli, HttpStatus.OK);
		}
	}

	// Inserimento articolo
	@ApiOperation(value = "Inserisci l'articolo", notes = "Restituisce un messaggio di stato in formato JSON", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Articolo Inserito"),
			@ApiResponse(code = 400, message = "Dati Articolo in Formato Errato"),
			@ApiResponse(code = 406, message = "Articolo Già Presente")})
	@PostMapping(value = "/inserisci")
	public ResponseEntity<?> createArticolo(@Valid @RequestBody Articoli articolo, BindingResult bindingResult)
			throws BindingException, DuplicateException {
		logger.info("****** Salviamo l'articolo con codice " + articolo.getCodArt() + " *******");
		if (bindingResult.hasErrors()) {
			String errMsg = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			logger.warn(errMsg);
			throw new BindingException(errMsg);
		} else {
			Articoli checkArt = articoliService.getOne(articolo.getCodArt(), "");
			if (checkArt != null) {
				String errMsg = "Articolo " + articolo.getCodArt()
						+ " presente in anagrafica! Impossibile utilizzare il metodo POST";
				logger.warn(errMsg);
				throw new DuplicateException(errMsg);
			} else {
				articoliService.save(articolo);
				ObjectMapper mapper = new ObjectMapper();
				ObjectNode responseNode = mapper.createObjectNode();
				responseNode.put("code", HttpStatus.OK.toString());
				responseNode.put("message", "Inserimento Articolo " + articolo.getCodArt() + " Eseguita Con Successo");
				return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.CREATED);
			}
		}
	}

	// Modifica articolo
	@ApiOperation(value = "Modifica l'articolo", notes = "Restituisce un messaggio di stato in formato JSON", produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Articolo Modificato"),
			@ApiResponse(code = 400, message = "Dati Articolo in Formato Errato"),
			@ApiResponse(code = 404, message = "Articolo Non Trovato")})
	@PutMapping(value = "/modifica")
	public ResponseEntity<?> updateArticolo(@Valid @RequestBody Articoli articolo, BindingResult bindingResult)
			throws BindingException, NotFoundException {
		logger.info("****** Modifichiamo l'articolo con codice " + articolo.getCodArt() + " *******");
		if (bindingResult.hasErrors()) {
			String errMsg = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			logger.warn(errMsg);
			throw new BindingException(errMsg);
		} else {
			Articoli checkArt = articoliService.getOne(articolo.getCodArt(), "");
			if (checkArt == null) {
				String errMsg = "Articolo " + articolo.getCodArt()
						+ " non presente in anagrafica! Impossibile utilizzare il metodo PUT";
				logger.warn(errMsg);
				throw new NotFoundException(errMsg);
			} else {
				articoliService.save(articolo);
				ObjectMapper mapper = new ObjectMapper();
				ObjectNode responseNode = mapper.createObjectNode();
				responseNode.put("code", HttpStatus.OK.toString());
				responseNode.put("message", "Modifica Articolo " + articolo.getCodArt() + " Eseguita Con Successo");
				return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.CREATED);
			}
		}
	}

	// Eliminazione articolo
	@ApiOperation(value = "Elimina l'articolo", notes = "Restituisce un messaggio di stato in formato JSON", produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Articolo Eliminato"),
			@ApiResponse(code = 404, message = "Articolo Non Trovato")})
	@DeleteMapping(value = "/elimina/{codArt}", produces = "application/json")
	public ResponseEntity<?> deleteArticolo(@PathVariable String codArt) throws NotFoundException {
		logger.info("****** Eliminiamo l'articolo con codice " + codArt + " *******");
		Articoli articolo = articoliService.getOne(codArt, "");
		if (articolo == null) {
			String errMsg = "Articolo " + codArt
					+ " non presente in anagrafica! Impossibile utilizzare il metodo DELETE";
			logger.warn(errMsg);
			throw new NotFoundException(errMsg);
		} else {
			articoliService.delete(articolo);
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode responseNode = mapper.createObjectNode();
			responseNode.put("code", HttpStatus.OK.toString());
			responseNode.put("message", "Eliminazione Articolo " + codArt + " Eseguita Con Successo");
			return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
		}
	}

}
