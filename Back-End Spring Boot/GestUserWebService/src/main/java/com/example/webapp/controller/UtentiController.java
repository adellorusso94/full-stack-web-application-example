package com.example.webapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp.exception.BindingException;
import com.example.webapp.exception.NotFoundException;
import com.example.webapp.model.Utenti;
import com.example.webapp.service.UtentiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = "/api/utenti")
public class UtentiController {

	@Autowired
	UtentiService utentiService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ResourceBundleMessageSource errMessage;

	@GetMapping(value = "/cerca/tutti")
	public List<Utenti> getUtenti() {
		return utentiService.getAll();
	}

	@GetMapping(value = "/cerca/username/{username}")
	public Utenti getUtente(@PathVariable("username") String username) {
		Utenti retVal = utentiService.getOne(username);
		return retVal;
	}

	@PostMapping(value = "/inserisci")
	public ResponseEntity<?> addNewUser(@Valid @RequestBody Utenti utente, BindingResult bindingResult)
			throws BindingException {
		if (bindingResult.hasErrors()) {
			String msgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			throw new BindingException(msgErr);
		}
		String encodedPassword = passwordEncoder.encode(utente.getPassword());
		utente.setPassword(encodedPassword);
		utentiService.save(utente);
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectNode responseNode = mapper.createObjectNode();
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Inserimento Utente " + utente.getUsername() + " Eseguita Con Successo");
		return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/elimina/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username) throws NotFoundException {
		Utenti utente = utentiService.getOne(username);
		if (utente == null) {
			String msgErr = String.format("Utente %s non presente in anagrafica! ", username);
			throw new NotFoundException(msgErr);
		}
		utentiService.delete(utente);
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectNode responseNode = mapper.createObjectNode();
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Utente " + username + " Eseguita Con Successo");
		return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
	}
	
}
