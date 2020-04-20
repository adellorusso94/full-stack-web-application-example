package com.example.webapp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
 
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.example.webapp.entity.Promo;
import com.example.webapp.exception.PromoNotFoundException;
import com.example.webapp.service.PromoService;

@RestController
@RequestMapping(value = "api/promo")
public class PromoController {
	
	@Autowired
	private PromoService promoService;
	
	@Autowired
	ArticoliClient articoliClient;
		
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Promo>> listAllPromo() {
		System.out.println("****** Otteniamo tutte le promozioni *******");
		List<Promo> lista_promo = promoService.SelTutti();
		if (lista_promo.isEmpty()) {
			return new ResponseEntity<List<Promo>>(HttpStatus.NO_CONTENT);
		} else {
			System.out.println("Numero dei record: " + lista_promo.size());
			return new ResponseEntity<List<Promo>>(lista_promo, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/id/{idPromo}", produces = "application/json")
	public ResponseEntity<Promo> listPromoById(@PathVariable String idPromo) 
			 throws PromoNotFoundException {
		System.out.println("****** Otteniamo la promozione con Id: " + idPromo + "*******");
		Promo promo = promoService.SelByIdPromo(idPromo);
		if (promo == null) {
			throw new PromoNotFoundException("Promozione Assente o Id Errato");
		} else {
			return new ResponseEntity<Promo>(promo, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/codice", produces = "application/json")
	public ResponseEntity<Promo> listPromoByCodice(@RequestParam("anno") String anno,
			@RequestParam("codice") String codice) {
		System.out.println("****** Otteniamo la promozione con Codice: " + codice + "*******");
		Promo promo = promoService.SelByAnnoCodice(anno, codice);
		if (promo == null) {
			return new ResponseEntity<Promo>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Promo>(promo, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/active", produces = "application/json")
	public ResponseEntity<List<Promo>> listPromoActive() {
		System.out.println("****** Otteniamo la Promozione Attive*******");
		List<Promo> lista_promo = promoService.SelPromoActive();
		if (lista_promo == null) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			for (Promo promozione : lista_promo) {
				promozione.getDettPromo().forEach(f -> f.setDescrizione(articoliClient.getArticolo(f.getCodart()).getDescrizione()));
				promozione.getDettPromo().forEach(f -> f.setPrezzo(articoliClient.getArticolo(f.getCodart()).getPrezzo()));
			}
			return new ResponseEntity<List<Promo>>(lista_promo, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/inserisci", produces = "application/json")
	public ResponseEntity<Promo> createPromo(@RequestBody Promo promo) {
		UUID uuid = UUID.randomUUID();
	    String GUID = uuid.toString();
	    System.out.println("***** Creiamo una Promo con id " + GUID + " *****");
		if (promo.getIdPromo().length() != 0) {
			System.out.println("Impossibile modificare con il metodo POST ");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
		    promo.setIdPromo(GUID);
		    promoService.InsPromo(promo);
		    return new ResponseEntity<Promo>(new HttpHeaders(), HttpStatus.CREATED);
		}
	}
	
	@PutMapping(value = "/modifica")
	public ResponseEntity<Promo> updatePromo(@RequestBody Promo promo) {
		 System.out.println("***** Modifichiamo la Promo con id " + promo.getIdPromo() + " *****");
		 if (promo.getIdPromo().length() <= 0) {
			 System.out.println("Impossibile modificare una promozione priva di id! ");
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 } else {
			 promoService.InsPromo(promo);
			 return new ResponseEntity<Promo>(new HttpHeaders(), HttpStatus.CREATED);
		 }
	}
	
	@DeleteMapping(value = "/elimina/{idPromo}")
	public ResponseEntity<?> deletePromo(@PathVariable String idPromo)  {
		System.out.println("Eliminiamo la promo con id " + idPromo);
		Promo promo = promoService.SelByIdPromo(idPromo);
		if (promo == null) {
			System.out.println("Impossibile trovare la promo con id " + idPromo);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			promoService.DelPromo(promo);
			HttpHeaders headers = new HttpHeaders();
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode responseNode = mapper.createObjectNode();
			responseNode.put("code", HttpStatus.OK.toString());
			responseNode.put("message", "Eliminazione Promozione " + idPromo + " Eseguita Con Successo!");
			return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
		}
	}
	
}