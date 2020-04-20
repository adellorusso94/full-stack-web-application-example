package com.example.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

@RestController
@RequestMapping("api/articoli")
public class ArticoliController {

	@Autowired
	private ArticoliService articoliService;

	@Autowired
	private BarcodeService barcodeService;

	@Autowired
	private PriceClient priceClient;

	@Autowired
	private PromoClient promoClient;

	@Autowired
	private ResourceBundleMessageSource errMessage;

	private Double getPrezzo(String codArt, String idList, String Header) {
		if (idList.length() > 0) {
			return priceClient.getPrezzo(Header, codArt, idList);
		} else {
			return priceClient.getPrezzo(Header, codArt);
		}
	}

	private Double getPrezzoNoAuth(String codArt, String idList) {
		if (idList.length() > 0) {
			return priceClient.getPrezzoNoAuth(codArt, idList);
		} else {
			return priceClient.getPrezzoNoAuth(codArt);
		}
	}

	private Double getPromo(String codArt, String header) {
		return promoClient.getPromoArt(header, codArt);
	}

	// Test di connessione
	@GetMapping(value = "/test")
	public ResponseEntity<?> testConnex() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Test Connessione OK");
		return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
	}

	// Ricerca articolo per barcode
	@GetMapping(value = "/cerca/ean/{barcode}", produces = "application/json")
	public ResponseEntity<Articoli> getArticoloByEan(@PathVariable String barcode, HttpServletRequest httpRequest)
			throws NotFoundException {
		Barcode ean = barcodeService.getOne(barcode);
		if (ean == null) {
			String errMsg = "Il barcode " + barcode + " non è stato trovato!";
			throw new NotFoundException(errMsg);
		} else {
			String AuthHeader = httpRequest.getHeader("Authorization");
			ean.getArticolo().setPrezzo(this.getPrezzo(ean.getArticolo().getCodArt(), "", AuthHeader));
			ean.getArticolo().setPromo(this.getPromo(ean.getArticolo().getCodArt(), AuthHeader));
			return new ResponseEntity<Articoli>(ean.getArticolo(), HttpStatus.OK);
		}
	}

	// Ricerca articolo per codice (NO AUTH)
	@GetMapping(value = "/noauth/cerca/codice/{codArt}", produces = "application/json")
	public ResponseEntity<Articoli> getArticoloByCodArtNoAuth(@PathVariable String codArt) throws NotFoundException {
		Articoli articolo = articoliService.getOne(codArt);
		if (articolo == null) {
			String errMsg = "L'articolo con codice " + codArt + " non è stato trovato!";
			throw new NotFoundException(errMsg);
		} else {
			articolo.setPrezzo(this.getPrezzoNoAuth(articolo.getCodArt(), ""));
			return new ResponseEntity<Articoli>(articolo, HttpStatus.OK);
		}
	}

	// Ricerca articolo per codice
	@GetMapping(value = "/cerca/codice/{codArt}", produces = "application/json")
	public ResponseEntity<Articoli> getArticoloByCodArt(@PathVariable String codArt, HttpServletRequest httpRequest)
			throws NotFoundException {
		Articoli articolo = articoliService.getOne(codArt);
		if (articolo == null) {
			String errMsg = "L'articolo con codice " + codArt + " non è stato trovato!";
			throw new NotFoundException(errMsg);
		} else {
			String AuthHeader = httpRequest.getHeader("Authorization");
			articolo.setPrezzo(this.getPrezzo(articolo.getCodArt(), "", AuthHeader));
			articolo.setPromo(this.getPromo(articolo.getCodArt(), AuthHeader));
			return new ResponseEntity<Articoli>(articolo, HttpStatus.OK);
		}
	}

	// Ricerca articolo per descrizione
	@GetMapping(value = "/cerca/descrizione/{filter}", produces = "application/json")
	public ResponseEntity<List<Articoli>> getArticoloByDescrizione(@PathVariable String filter,
			HttpServletRequest httpRequest) throws NotFoundException {
		List<Articoli> lista_articoli = articoliService.getList(filter.toUpperCase() + "%");
		if (lista_articoli.isEmpty()) {
			String errMsg = "Non è stato trovato alcun articolo avente descrizione " + filter + ".";
			throw new NotFoundException(errMsg);
		} else {
			String AuthHeader = httpRequest.getHeader("Authorization");
			lista_articoli.forEach(f -> f.setPrezzo(this.getPrezzo(f.getCodArt(), "", AuthHeader)));
			lista_articoli.forEach(f -> f.setPromo(this.getPromo(f.getCodArt(), AuthHeader)));
			return new ResponseEntity<List<Articoli>>(lista_articoli, HttpStatus.OK);
		}
	}

	// Inserimento articolo
	@PostMapping(value = "/inserisci")
	public ResponseEntity<?> createArticolo(@Valid @RequestBody Articoli articolo, BindingResult bindingResult)
			throws BindingException, DuplicateException {
		if (bindingResult.hasErrors()) {
			String errMsg = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			throw new BindingException(errMsg);
		} else {
			Articoli checkArt = articoliService.getOne(articolo.getCodArt());
			if (checkArt != null) {
				String errMsg = "Articolo " + articolo.getCodArt()
						+ " presente in anagrafica! Impossibile utilizzare il metodo POST";
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
	@PutMapping(value = "/modifica")
	public ResponseEntity<?> updateArticolo(@Valid @RequestBody Articoli articolo, BindingResult bindingResult)
			throws BindingException, NotFoundException {
		if (bindingResult.hasErrors()) {
			String errMsg = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			throw new BindingException(errMsg);
		} else {
			Articoli checkArt = articoliService.getOne(articolo.getCodArt());
			if (checkArt == null) {
				String errMsg = "Articolo " + articolo.getCodArt()
						+ " non presente in anagrafica! Impossibile utilizzare il metodo PUT";
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
	@DeleteMapping(value = "/elimina/{codArt}", produces = "application/json")
	public ResponseEntity<?> deleteArticolo(@PathVariable String codArt) throws NotFoundException {
		Articoli articolo = articoliService.getOne(codArt);
		if (articolo == null) {
			String errMsg = "Articolo " + codArt
					+ " non presente in anagrafica! Impossibile utilizzare il metodo DELETE";
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
