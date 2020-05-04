package com.example.webapp.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
 
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.example.webapp.entity.Promo;
import com.example.webapp.exception.PromoNotFoundException;
import com.example.webapp.service.PromoService;

@RestController
public class PromoController {
	
	private static final Logger logger = LoggerFactory.getLogger(PromoController.class);
	
	@Autowired
	private PromoService promoService;
	
	@ApiOperation(value = "Ricerca promozioni", notes = "Restituisce i dati delle promozioni in formato JSON", response = List.class, produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Promozioni Trovate"),
			@ApiResponse(code = 204, message = "Promozioni Non Trovate") })
	@GetMapping(produces = "application/json")
	@RefreshScope
	public ResponseEntity<List<Promo>> listAllPromo() {
		logger.info("****** Otteniamo tutte le promozioni *******");
		List<Promo> lista_promo = promoService.getAll();
		if (lista_promo.isEmpty()) {
			return new ResponseEntity<List<Promo>>(HttpStatus.NO_CONTENT);
		} else {
			logger.info("Numero dei record: " + lista_promo.size());
			return new ResponseEntity<List<Promo>>(lista_promo, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Ricerca promozione per id", notes = "Restituisce i dati della promozione in formato JSON", response = Promo.class, produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Promozione Trovata"),
			@ApiResponse(code = 404, message = "Promozione Non Trovata") })
	@GetMapping(value = "/id/{idPromo}", produces = "application/json")
	@RefreshScope
	public ResponseEntity<Promo> listPromoById(@PathVariable String idPromo) 
			 throws PromoNotFoundException {
		logger.info("****** Otteniamo la promozione con id " + idPromo + " *******");
		Promo promo = promoService.getOne(idPromo);
		if (promo == null) {
			String errMsg = "Promozione Assente o Id Errato";
			logger.warn(errMsg);
			throw new PromoNotFoundException(errMsg);
		} else {
			return new ResponseEntity<Promo>(promo, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Ricerca promozione per codice", notes = "Restituisce i dati della promozione in formato JSON", response = Promo.class, produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Promozione Trovata"),
			@ApiResponse(code = 204, message = "Promozione Non Trovata") })
	@GetMapping(value = "/codice", produces = "application/json")
	@RefreshScope
	public ResponseEntity<Promo> listPromoByCodice(@RequestParam("anno") String anno,
			@RequestParam("codice") String codice) {
		logger.info("****** Otteniamo la promozione con codice " + codice + " *******");
		Promo promo = promoService.getOne(anno, codice);
		if (promo == null) {
			return new ResponseEntity<Promo>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Promo>(promo, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Ricerca promozioni attive", notes = "Restituisce i dati delle promozioni in formato JSON", response = List.class, produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Promozioni Trovate"),
			@ApiResponse(code = 204, message = "Promozioni Non Trovate") })
	@GetMapping(value = "/active", produces = "application/json")
	@RefreshScope
	public ResponseEntity<List<Promo>> listPromoActive() {
		logger.info("****** Otteniamo la Promozione Attive *******");
		List<Promo> lista_promo = promoService.getAllActive();
		if (lista_promo == null) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Promo>>(lista_promo, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Inserisci la promozione", notes = "Restituisce i dati della promozione in formato JSON", response = Promo.class, produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Promozione Inserita"),
			@ApiResponse(code = 400, message = "Dati Promozione in Formato Errato")})
	@PostMapping(value = "/inserisci", produces = "application/json")
	public ResponseEntity<Promo> createPromo(@RequestBody Promo promo) {
		UUID uuid = UUID.randomUUID();
	    String GUID = uuid.toString();
	   logger.info("****** Creiamo una promo con id " + GUID + " *******");
		if (promo.getIdPromo().length() != 0) {
			logger.warn("Impossibile modificare con il metodo POST ");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
		    promo.setIdPromo(GUID);
		    promoService.save(promo);
		    return new ResponseEntity<Promo>(new HttpHeaders(), HttpStatus.CREATED);
		}
	}
	
	@ApiOperation(value = "Modifica la promozione", notes = "Restituisce un messaggio di stato in formato JSON", response = Promo.class, produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Promozione Modificata"),
			@ApiResponse(code = 400, message = "Dati Promozione in Formato Errato")})
	@PutMapping(value = "/modifica")
	public ResponseEntity<Promo> updatePromo(@RequestBody Promo promo) {
		 logger.info("****** Modifichiamo la promo con id " + promo.getIdPromo() + " *******");
		 if (promo.getIdPromo().length() <= 0) {
			 logger.warn("Impossibile modificare una promozione priva di id! ");
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 } else {
			 promoService.save(promo);
			 return new ResponseEntity<Promo>(new HttpHeaders(), HttpStatus.CREATED);
		 }
	}
	
	@ApiOperation(value = "Elimina la promozione", notes = "Restituisce un messaggio di stato in formato JSON", produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Promozione Eliminata"),
			@ApiResponse(code = 204, message = "Promozione Non Trovata")})
	@DeleteMapping(value = "/elimina/{idPromo}")
	public ResponseEntity<?> deletePromo(@PathVariable String idPromo)  {
		logger.info("****** Eliminiamo la promo con id " + idPromo + " *******");
		Promo promo = promoService.getOne(idPromo);
		if (promo == null) {
			logger.warn("Impossibile trovare la promo con id " + idPromo);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			promoService.delete(promo);
			HttpHeaders headers = new HttpHeaders();
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode responseNode = mapper.createObjectNode();
			responseNode.put("code", HttpStatus.OK.toString());
			responseNode.put("message", "Eliminazione Promozione " + idPromo + " Eseguita Con Successo!");
			return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
		}
	}
	
}