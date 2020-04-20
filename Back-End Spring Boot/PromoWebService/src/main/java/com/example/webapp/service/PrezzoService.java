package com.example.webapp.service;

public interface PrezzoService {
	
	Double SelPromoByCodArt(String codArt);
	Double SelPromoByCodArtAndFid(String codArt);
	Double SelByCodArtAndCodFid(String codArt, String codFid);
	void UpdOggettoPromo(String oggetto, Long id);
	
}
