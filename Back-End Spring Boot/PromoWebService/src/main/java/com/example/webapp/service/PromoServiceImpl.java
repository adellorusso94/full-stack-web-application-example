package com.example.webapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Promo;
import com.example.webapp.feign.ArticoliClient;
import com.example.webapp.repository.PromoRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
@Transactional(readOnly = true)
public class PromoServiceImpl implements PromoService {
	
	private static final Logger logger = LoggerFactory.getLogger(PromoServiceImpl.class);
	
	@Autowired
	private PromoRepository promoRepository;
	
	@Autowired
	private ArticoliClient articoliClient;
	
	//HYSTRIX CONSTANT SETTINGS
	public static final String FailureTimOutInMs = "15000";
	public static final String FailNumToOpCirc = "10";
	public static final String ErrPercToOpCirc = "20";
	public static final String SleepTimeInMs = "4000";
	public static final String TimeMetricInMs = "10000";

	@Override
	@Cacheable(value = "listpromo_cache", sync = true)
	public List<Promo> getAll() {
		return promoRepository.findAll();
	}

	@Override
	@Cacheable(value = "promo_cache", key = "#idPromo", sync = true)
	public Promo getOne(String idPromo) {
		return promoRepository.findByIdPromo(idPromo);
	}

	@Override
	@Cacheable(value = "promo2_cache", key = "#codice", sync = true)
	public Promo getOne(String anno, String codice) {
		return promoRepository.findByAnnoAndCodice(Integer.parseInt(anno), codice);
	}

	@Override
	@Cacheable(value = "listactive_cache", sync = true)
	@HystrixCommand(fallbackMethod = "getAllActiveFallback",
	commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = FailureTimOutInMs),
		    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = FailNumToOpCirc),
		    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = ErrPercToOpCirc),
		    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = SleepTimeInMs),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = TimeMetricInMs)
	})
	public List<Promo> getAllActive() {
		List<Promo> lista_promo = promoRepository.SelPromoActive();
		for (Promo promozione : lista_promo) {
			promozione.getDettPromo().forEach(f -> f.setDescrizione(articoliClient.getArticolo(f.getCodart()).getDescrizione()));
			promozione.getDettPromo().forEach(f -> f.setPrezzo(articoliClient.getArticolo(f.getCodart()).getPrezzo()));
		}
		return lista_promo;
	}
	
	public List<Promo> getAllActiveFallback() {
		logger.warn("****** getAllActiveFallback di PromoService in esecuzione *******");
		List<Promo> lista_promo = promoRepository.SelPromoActive();
		for (Promo promozione : lista_promo) {
			promozione.getDettPromo().forEach(f -> f.setDescrizione("Non Disponibile"));
			promozione.getDettPromo().forEach(f -> f.setPrezzo(0.00));
		}
		return lista_promo;
	}

	@Override
	@Transactional
	@Caching(evict = { 
		@CacheEvict(cacheNames="listpromo_cache", allEntries = true),
		@CacheEvict(cacheNames="listactive_cache", allEntries = true),
		@CacheEvict(cacheNames="promo_cache", key = "#promo.idPromo"),
		@CacheEvict(cacheNames="promo2_cache", key = "#promo.codice")
	})
	public void save(Promo promo) {
		promoRepository.saveAndFlush(promo);
	}

	@Override
	@Transactional
	@Caching(evict = { 
		@CacheEvict(cacheNames="listpromo_cache", allEntries = true),
		@CacheEvict(cacheNames="listactive_cache", allEntries = true),
		@CacheEvict(cacheNames="promo_cache", key = "#promo.idPromo"),
		@CacheEvict(cacheNames="promo2_cache", key = "#promo.codice")
	})
	public void delete(Promo promo) {
		promoRepository.delete(promo);
	}

}