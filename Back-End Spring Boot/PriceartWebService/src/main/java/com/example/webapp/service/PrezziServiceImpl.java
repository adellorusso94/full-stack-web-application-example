package com.example.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.DettListini;
import com.example.webapp.entity.Listini;
import com.example.webapp.repository.ListinoRepository;
import com.example.webapp.repository.PrezziRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
@Transactional
public class PrezziServiceImpl implements PrezziService {
	
	private static final Logger logger = LoggerFactory.getLogger(PrezziServiceImpl.class);
	
	@Autowired
	CacheManager cacheManager;
	
	//HYSTRIX CONSTANT SETTINGS
	public static final String FailureTimOutInMs = "15000";
	public static final String FailNumToOpCirc = "10";
	public static final String ErrPercToOpCirc = "20";
	public static final String SleepTimeInMs = "4000";
	public static final String TimeMetricInMs = "10000";
	
	@Autowired
	PrezziRepository prezziRepository;
	
	@Autowired
	ListinoRepository listinoRepository;
	
	@Override
	@Cacheable(value = "cacheprezzo", key = "#codArt", sync = true)
	@HystrixCommand(fallbackMethod = "getOneFallback",
	commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = FailureTimOutInMs),
		    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = FailNumToOpCirc),
		    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = ErrPercToOpCirc),
		    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = SleepTimeInMs),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = TimeMetricInMs)
	})
	public DettListini getOne(String codArt, String idList) {
		return prezziRepository.SelByCodArtAndList(codArt, idList);
	}
	
	public DettListini getOneFallback(String codArt, String idList) {
		logger.warn("****** getOneFallback di PrezziService in esecuzione *******");
		ValueWrapper w = cacheManager.getCache("cacheprezzo").get(codArt);
		if (w != null) {
			return (DettListini) w.get();
		} else {
			DettListini dettListini = new DettListini();
			Listini listino = listinoRepository.getOne(idList);
			dettListini.setCodArt(codArt);
			dettListini.setListino(listino);
			dettListini.setPrezzo(0.0);
			return dettListini;
		}
	}
	
	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = "cacheprezzo", key = "#dettListini.codArt")
	})
	public void save(DettListini dettListini) {
		prezziRepository.save(dettListini);
	}
	
	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = "cacheprezzo", key = "#codArt")
	})
	public void delete(String codArt, String idList) {
		prezziRepository.DelRowDettList(codArt, idList);
	}

}
