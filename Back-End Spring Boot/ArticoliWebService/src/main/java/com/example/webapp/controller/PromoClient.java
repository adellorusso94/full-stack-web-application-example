package com.example.webapp.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="Promo", url="localhost:8091")
public interface PromoClient {
	
	@GetMapping(value="api/promo/prezzo/{codArt}")
	public Double getPromoArt(@RequestHeader("Authorization") String AuthHeader, @PathVariable String codArt);
	
	@GetMapping(value = "api/promo/prezzo/fidelity/{codArt}")
    public Double getPromoArtFid(@RequestHeader("Authorization") String AuthHeader, @PathVariable String codArt);
	
	@GetMapping(value = "api/promo/prezzo/{codArt}/{codFid}")
    public Double getPromoOnlyFid(@RequestHeader("Authorization") String AuthHeader, @PathVariable String codArt, @PathVariable String codFid);
	
}