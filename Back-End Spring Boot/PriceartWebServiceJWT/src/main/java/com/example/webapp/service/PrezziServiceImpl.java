package com.example.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.DettListini;
import com.example.webapp.repository.PrezziRepository;

@Service
@Transactional
public class PrezziServiceImpl implements PrezziService {
	
	@Autowired
	private PrezziRepository prezziRepository;
	
	@Override
	@Cacheable(value = "cacheprezzo", key = "#codArt", sync = true)
	public DettListini getOne(String codArt, String idList) {
		return prezziRepository.SelByCodArtAndList(codArt, idList);
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
