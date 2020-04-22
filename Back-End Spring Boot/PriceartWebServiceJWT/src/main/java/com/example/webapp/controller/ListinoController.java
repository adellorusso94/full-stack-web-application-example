package com.example.webapp.controller;

import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp.entity.Listini;
import com.example.webapp.exception.BindingException;
import com.example.webapp.exception.DuplicateException;
import com.example.webapp.exception.NotFoundException;
import com.example.webapp.service.ListinoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/listino")
public class ListinoController {

	@Autowired
	private ListinoService listinoService;

	@Autowired
	private ResourceBundleMessageSource errMessage;

	// Ricerca listino per id
	@ApiOperation(value = "Ricerca listino per id", notes = "Restituisce i dati degli articoli in formato JSON", produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Listino Trovato"),
			@ApiResponse(code = 404, message = "Listino Non Trovato") })
	@GetMapping(value = "/cerca/id/{idList}", produces = "application/json")
	public ResponseEntity<Optional<Listini>> getListino(@PathVariable String idList) throws NotFoundException {
		Optional<Listini> listini = listinoService.getOne(idList);
		if (listini == null) {
			String errMsg = "Il listino " + idList + " non è stato trovato!";
			throw new NotFoundException(errMsg);
		} else {
			return new ResponseEntity<Optional<Listini>>(listini, HttpStatus.OK);
		}
	}

	// Inserimento listino
	@ApiOperation(value = "Inserisci il listino", notes = "Restituisce un messaggio di stato in formato JSON", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Listino Inserito"),
			@ApiResponse(code = 400, message = "Dati Listino in Formato Errato"),
			@ApiResponse(code = 406, message = "Listino Già Presente")})
	@PostMapping(value = "/inserisci", produces = "application/json")
	public ResponseEntity<?> createListino(@Valid @RequestBody Listini listino, BindingResult bindingResult) throws BindingException, DuplicateException {
		if (bindingResult.hasErrors()) {
			String errMsg = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			throw new BindingException(errMsg);
		} else {
			listinoService.save(listino);
			HttpHeaders headers = new HttpHeaders();
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode responseNode = mapper.createObjectNode();
			responseNode.put("code", HttpStatus.OK.toString());
			responseNode.put("message", "Inserimento Listino " + listino.getId() + " Eseguito Con Successo");
			return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
		}
	}

	// Eliminazione articolo
	@ApiOperation(value = "Elimina il listino", notes = "Restituisce un messaggio di stato in formato JSON", produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Listino Eliminato"),
			@ApiResponse(code = 404, message = "Listino Non Trovato")})
	@DeleteMapping(value = "/elimina/{idList}", produces = "application/json")
	public ResponseEntity<?> deleteListino(@PathVariable String idList) throws NotFoundException {
		Optional<Listini> listino = listinoService.getOne(idList);
		if (!listino.isPresent()) {
			String errMsg = "Listino " + idList + " non presente in anagrafica!";
			throw new NotFoundException(errMsg);
		} else {
			listinoService.delete(listino.get());
			HttpHeaders headers = new HttpHeaders();
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode responseNode = mapper.createObjectNode();
			responseNode.put("code", HttpStatus.OK.toString());
			responseNode.put("message", "Eliminazione Listino " + idList + " Eseguita Con Successo");
			return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
		}
	}

}
