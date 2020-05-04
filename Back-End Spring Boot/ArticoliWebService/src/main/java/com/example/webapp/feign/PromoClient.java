package com.example.webapp.feign;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="Zuul")
@RibbonClient(name="Promo")
public interface PromoClient {
	
	@GetMapping(value="/api/promo/prezzo/{codArt}")
	public Double getPromoArt(@RequestHeader("AUTHORIZATION") String AuthHeader, @PathVariable String codArt);
	
	@GetMapping(value = "/api/promo/prezzo/fidelity/{codArt}")
    public Double getPromoArtFid(@RequestHeader("AUTHORIZATION") String AuthHeader, @PathVariable String codArt);
	
	@GetMapping(value = "/api/promo/prezzo/{codArt}/{codFid}")
    public Double getPromoOnlyFid(@RequestHeader("AUTHORIZATION") String AuthHeader, @PathVariable String codArt, @PathVariable String codFid);
	
}
