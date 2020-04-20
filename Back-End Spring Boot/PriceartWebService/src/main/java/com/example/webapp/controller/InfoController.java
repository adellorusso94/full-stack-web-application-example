package com.example.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp.appconf.AppConfig;

@RestController
public class InfoController {
	
	@Autowired
	private AppConfig configuration;
	
	@GetMapping("/info")
	public Map<String, String> getInfo() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("listino", configuration.getListino());
		return map;
	}

}
