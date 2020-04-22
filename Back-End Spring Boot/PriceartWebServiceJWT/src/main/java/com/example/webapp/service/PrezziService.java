package com.example.webapp.service;

import com.example.webapp.entity.DettListini;

public interface PrezziService {
	
	public DettListini getOne(String codArt, String idList);
	public void save(DettListini dettListini);
	public void delete(String codArt, String idList);
	
}
