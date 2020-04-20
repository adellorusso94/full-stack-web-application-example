package com.example.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	public DettListini getPrezzo(String codArt, String idList) {
		return prezziRepository.SelByCodArtAndList(codArt, idList);
	}

	@Override
	public void deletePrezzo(String codArt, String idList) {
		prezziRepository.DelRowDettList(codArt, idList);
	}

}
