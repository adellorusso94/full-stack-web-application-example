package com.example.webapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Articoli;
import com.example.webapp.feign.PriceClient;
import com.example.webapp.feign.PromoClient;
import com.example.webapp.repository.ArticoliRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
@Transactional(readOnly = true)
public class ArticoliServiceImpl implements ArticoliService {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticoliServiceImpl.class);
	
	@Autowired
	CacheManager cacheManager;
	
	@Autowired
	private PriceClient priceClient;
	
	@Autowired
	private PromoClient promoClient;
	
	@Autowired
	ArticoliRepository articoliRepository;
	
	//HYSTRIX CONSTANT SETTINGS
	public static final String FailureTimOutInMs = "15000";
	public static final String FailNumToOpCirc = "10";
	public static final String ErrPercToOpCirc = "20";
	public static final String SleepTimeInMs = "4000";
	public static final String TimeMetricInMs = "10000";
	
	private Double getPrezzo(String codArt, String idList, String Header) {
		double prezzo = 0.00;
		try {
			if (idList != "") {
				prezzo = priceClient.getPrezzo(Header.trim(), codArt, idList);
			} else {
				prezzo = priceClient.getPrezzo(Header.trim(), codArt);
			}
			logger.info("Prezzo Articolo " + codArt + ": " + prezzo);
		} catch (Exception e) {
			logger.warn("Errore: " + e.getMessage());
		}
		return prezzo;
	}
	
	private Double getPrezzoNoAuth(String codArt, String idList) {
		double prezzo = 0.00;
		try {
			if (idList != "") {
				prezzo = priceClient.getPrezzoNoAuth(codArt, idList);
			} else {
				prezzo = priceClient.getPrezzoNoAuth(codArt);
			}
			logger.info("Prezzo Articolo " + codArt + ": " + prezzo);
		} catch (Exception e) {
			logger.warn("Errore: " + e.getMessage());
		}
		return prezzo;
	}
	
	private Double getPromo(String codArt, String header) {
		double promo = 0.00;
		try {
			promo = promoClient.getPromoArt(header, codArt);
			logger.info("Promo Articolo " + codArt + ": " + promo);
		} catch (Exception e) {
			logger.warn("Errore: " + e.getMessage());
		}
		return promo;
	}
	
	@Override
	public Iterable<Articoli> getAll() {
		return articoliRepository.findAll();
	}

	@Override
	@Cacheable(value = "articolicache", sync = true)
	@HystrixCommand(fallbackMethod = "getListFallback",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = FailureTimOutInMs),
				    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = FailNumToOpCirc),
				    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = ErrPercToOpCirc),
				    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = SleepTimeInMs),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = TimeMetricInMs)
			})
	public List<Articoli> getList(String descrizione, String header) {
		List<Articoli> lista_articoli = articoliRepository.SelByDescrizioneLike(descrizione);
		if(lista_articoli != null) {
			lista_articoli.forEach(f -> f.setPrezzo(this.getPrezzo(f.getCodArt(), "", header)));
			lista_articoli.forEach(f -> f.setPromo(this.getPromo(f.getCodArt(), header)));
		}
		return lista_articoli;
	}
	
	@SuppressWarnings("unchecked")
	public List<Articoli> getListFallback(String descrizione, String header) {
		logger.warn("****** getListFallback di ArticoliService in esecuzione *******");
		ValueWrapper w = cacheManager.getCache("articolicache").get(descrizione);
		if (w != null) {
			return (List<Articoli>) w.get();
		} else {
			List<Articoli> lista_articoli = articoliRepository.SelByDescrizioneLike(descrizione);
			lista_articoli.forEach(f -> f.setPrezzo(0.00));
			lista_articoli.forEach(f -> f.setPromo(0.00));
			return lista_articoli;
		}
	}

	@Override
	@Cacheable(value = "articolicache", sync = true)
	public List<Articoli> getList(String descrizione, Pageable pageable, String header) {
		List<Articoli> lista_articoli = articoliRepository.findByDescrizioneLike(descrizione, pageable);
		if(lista_articoli != null) {
			lista_articoli.forEach(f -> f.setPrezzo(this.getPrezzo(f.getCodArt(), "", header)));
			lista_articoli.forEach(f -> f.setPromo(this.getPromo(f.getCodArt(), header)));
		}
		return lista_articoli;
	}

	@Override
	@Cacheable(value = "articolo", key = "#codArt", sync = true)
	@HystrixCommand(fallbackMethod = "getOneFallback",
	commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = FailureTimOutInMs),
		    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = FailNumToOpCirc),
		    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = ErrPercToOpCirc),
		    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = SleepTimeInMs),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = TimeMetricInMs)
	})
	public Articoli getOne(String codArt, String header) {
		Articoli articolo = articoliRepository.findByCodArt(codArt);
		if (articolo != null) {
			if (header != "") {
				articolo.setPrezzo(this.getPrezzo(articolo.getCodArt(), "", header));
			} else {
				articolo.setPrezzo(this.getPrezzoNoAuth(articolo.getCodArt(), ""));
			}
			articolo.setPromo(this.getPromo(codArt, header));
		}
		return articolo;
	}
	
	public Articoli getOneFallback(String codArt, String header) {
		logger.warn("****** getOneFallback di ArticoliService in esecuzione *******");
		ValueWrapper w = cacheManager.getCache("articolo").get(codArt);
		if (w != null) {
			return (Articoli) w.get();
		} else {
			Articoli articolo = new Articoli();
			articolo.setCodArt("-1");
			articolo.setDescrizione("Servizio in avaria");
			articolo.setPrezzo(0.00);
			articolo.setPromo(0.00);
			return articolo;
		}
	}

	@Override
	@Transactional
	@Caching(evict = {
			@CacheEvict(cacheNames = "articolicache", allEntries = true),
			@CacheEvict(cacheNames = "articolo", key = "#articolo.codArt")
	})
	public void delete(Articoli articolo) {
		articoliRepository.delete(articolo);
	}

	@Override
	@Transactional
	@Caching(evict = {
			@CacheEvict(cacheNames = "articolicache", allEntries = true),
			@CacheEvict(cacheNames = "articolo", key = "#articolo.codArt")
	})
	public void save(Articoli articolo) {
		articoliRepository.save(articolo);
	}

}
