package com.example.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.DettPromo;
import com.example.webapp.repository.PrezziPromoRepository;

@Service
@Transactional(readOnly = true)
public class PrezzoServiceImpl implements PrezzoService {
	
	@Autowired
	private PrezziPromoRepository prezziPromoRep;

	@Override
	@Cacheable(value = "prezzo_cache", key = "#codArt", sync = true)
	public Double getOneByCodArt(String codArt) {
		double retVal = 0;
		DettPromo promo =  prezziPromoRep.SelByCodArt(codArt);
		if (promo != null) {
			if (promo.getTipoPromo().getIdTipoPromo() == 1) {
				try {
					retVal = Double.parseDouble(promo.getOggetto().replace(",", "."));
				} catch(NumberFormatException ex) {
					ex.printStackTrace();
				}
			} else {
				// Da completare per altri tipi di promozioni
			}
		} else {
			System.out.println("Promo Articolo Assente!");
		}
		return retVal;
	}
	
	@Override
	@Cacheable(value = "prezzo2_cache", key = "#codArt", sync = true)
	public Double getOneByCodArtAndFid(String codArt) {
		double retVal = 0;
		DettPromo promo =  prezziPromoRep.SelByCodArtAndFid(codArt);
		if (promo != null) {
			if (promo.getTipoPromo().getIdTipoPromo() == 1) {
				try {
					retVal = Double.parseDouble(promo.getOggetto().replace(",", "."));
				} catch(NumberFormatException ex) {
					ex.printStackTrace();
				}
			} else {
				// Da completare per altri tipi di promozioni
			}
		} else {
			System.out.println("Promo Articolo Fidelity Assente!");
		}
		return retVal;
	}
	
	@Override
	@Cacheable(value = "prezzo3_cache", key = "#codArt", sync = true)
	public Double getOneByCodArtAndCodFid(String codArt, String codFid) {
		double retVal = 0;
		DettPromo promo =  prezziPromoRep.SelByCodArtAndCodFid(codArt, codFid);
		if (promo != null) {
			if (promo.getTipoPromo().getIdTipoPromo() == 1) {
				try {
					retVal = Double.parseDouble(promo.getOggetto().replace(",", "."));
				} catch(NumberFormatException ex) {
					ex.printStackTrace();
				}
			} else {
				// Da completare per altri tipi di promozioni
			}
		} else {
			System.out.println(String.format("Promo Riservata Fidelity %s Assente!!", codFid) );
		}
		return retVal;
	}
	
	@Override
	public void UpdOggettoPromo(String Oggetto, Long Id) {
		// TODO Auto-generated method stub
	}
	
}
