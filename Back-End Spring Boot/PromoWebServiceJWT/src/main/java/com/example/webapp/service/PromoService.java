package com.example.webapp.service;
 
import java.util.List;

import com.example.webapp.entity.Promo;

public interface PromoService {
	
	public List<Promo> getAll();
	public Promo getOne(String IdPromo);
	public Promo getOne(String Anno, String Codice);
	List<Promo> getAllActive();
	public void save(Promo promo);
	public void delete(Promo promo);
	
}
