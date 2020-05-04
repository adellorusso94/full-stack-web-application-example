package com.example.webapp.feign;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.webapp.entity.Articoli;

@FeignClient(name="Zuul")
@RibbonClient(name="Products")
public interface ArticoliClient {
	
	@GetMapping(value = "/api/articoli/noauth/cerca/codice/{codArt}")
    public Articoli getArticolo(@PathVariable String codArt);
	
}
