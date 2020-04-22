package com.example.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Promo;
import com.example.webapp.repository.PromoRepository;

@Service
@Transactional(readOnly = true)
public class PromoServiceImpl implements PromoService {
	
	@Autowired
	private PromoRepository promoRepository;

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
	public List<Promo> getAllActive() {
		return promoRepository.SelPromoActive();
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