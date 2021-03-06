package com.example.webapp.feign;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="Zuul")
@RibbonClient(name="PriceArt")
public interface PriceClient {
	
	@GetMapping(value="/api/prezzi/{codArt}")
	public Double getPrezzo(@RequestHeader("AUTHORIZATION") String AuthHeader, @PathVariable String codArt);
	
	@GetMapping(value="/api/prezzi/{codArt}/{idList}")
	public Double getPrezzo(@RequestHeader("AUTHORIZATION") String AuthHeader, @PathVariable String codArt, @PathVariable String idList);
	
	@GetMapping(value="/api/prezzi/noauth/{codArt}")
	public Double getPrezzoNoAuth(@PathVariable String codArt);
	
	@GetMapping(value="/api/prezzi/noauth/{codArt}/{idList}")
	public Double getPrezzoNoAuth(@PathVariable String codArt, @PathVariable String idList);
	
}
