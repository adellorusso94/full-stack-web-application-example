package com.example.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp.appconf.AppConfig;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class InfoController {
	
	@Autowired
	private AppConfig configuration;
	
	@ApiOperation(value = "Ottieni informazioni sul listino", notes = "Restituisce un messaggio di stato in formato JSON", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Informazioni Listino Ottenute") })
	@GetMapping("/info")
	public Map<String, String> getInfo() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("listino", configuration.getListino());
		return map;
	}

}
