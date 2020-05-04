package com.example.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Barcode;
import com.example.webapp.feign.PriceClient;
import com.example.webapp.repository.BarcodeRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
@Transactional(readOnly = true)
public class BarcodeServiceImpl implements BarcodeService{
	
	private static final Logger logger = LoggerFactory.getLogger(BarcodeServiceImpl.class);
	
	@Autowired
	CacheManager cacheManager;
	
	@Autowired
	private PriceClient priceClient;
	
	@Autowired
	BarcodeRepository barcodeRepository;
	
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
	
	@Override
	@Cacheable(value = "barcode", key = "#Barcode", sync = true)
	@HystrixCommand(fallbackMethod = "getOneFallback",
	commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = FailureTimOutInMs),
		    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = FailNumToOpCirc),
		    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = ErrPercToOpCirc),
		    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = SleepTimeInMs),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = TimeMetricInMs)
	})
	public Barcode getOne(String Barcode, String Header) {
		Barcode ean = barcodeRepository.findByBarcode(Barcode);
		ean.getArticolo().setPrezzo(this.getPrezzo(ean.getArticolo().getCodArt(), "", Header));
		return ean;
	}
	
	public Barcode getOneFallback(String Barcode, String Header) {
		logger.warn("****** getOneFallback di BarcodeService in esecuzione *******");
		ValueWrapper w = cacheManager.getCache("barcode").get(Barcode);
		if (w != null) {
			return (Barcode) w.get();
		} else {
			Barcode ean = barcodeRepository.findByBarcode(Barcode);
			ean.getArticolo().setPrezzo(0.00);
			return ean;
		}
	}

}
