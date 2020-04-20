package com.example.webapp.service;

import com.example.webapp.entity.DettListini;

public interface PrezziService {
	
	public DettListini getPrezzo(String codArt, String idList);
	public void deletePrezzo(String codArt, String idList);
	
}
