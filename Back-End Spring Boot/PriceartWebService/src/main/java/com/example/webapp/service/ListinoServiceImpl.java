package com.example.webapp.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Listini;
import com.example.webapp.repository.ListinoRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
@Transactional
public class ListinoServiceImpl implements ListinoService {
	
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
	ListinoRepository listinoRepository;
	
	@Override
	@Cacheable(value = "cachelistino", key = "#id", sync = true)
	@HystrixCommand(fallbackMethod = "getOneFallback",
	commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = FailureTimOutInMs),
		    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = FailNumToOpCirc),
		    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = ErrPercToOpCirc),
		    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = SleepTimeInMs),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = TimeMetricInMs)
	})
	public Optional<Listini> getOne(String id) {
		return listinoRepository.findById(id);
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Listini> getOneFallback(String id) {
		logger.warn("****** getOneFallback di ListinoService in esecuzione *******");
		ValueWrapper w = cacheManager.getCache("cachelistino").get(id);
		if (w != null) {
			return (Optional<Listini>) w.get();
		} else {
			Listini listino = new Listini();
			listino.setId("-1");
			listino.setDescrizione("Servizio in avaria");
			Optional<Listini> optListino = Optional.of(listino);
			return optListino;
		}
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = "cachelistino", key = "#listino.id")
	})
	public void save(Listini listino) {
		listinoRepository.save(listino);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = "cachelistino", key = "#listino.id")
	})
	public void delete(Listini listino) {
		listinoRepository.delete(listino);
	}

}
