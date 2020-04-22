package com.example.webapp.service;

public interface PrezzoService {
	
	Double getOneByCodArt(String codArt);
	Double getOneByCodArtAndFid(String codArt);
	Double getOneByCodArtAndCodFid(String codArt, String codFid);
	void UpdOggettoPromo(String oggetto, Long id);
	
}
