package com.example.webapp.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.example.webapp.entity.DettPromo;
import com.example.webapp.repository.PrezziPromoRepository;

@Service
@Transactional
public class PrezzoServiceImpl implements PrezzoService {
	
	@Autowired
	PrezziPromoRepository prezziPromoRep;

	@Override
	public Double SelPromoByCodArt(String codArt) {
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
				retVal = 0;
			}
		} else {
			System.out.println("Promo Articolo Assente!");
		}
		return retVal;
	}
	
	@Override
	public Double SelPromoByCodArtAndFid(String CodArt) {
		double retVal = 0;
		DettPromo promo =  prezziPromoRep.SelByCodArtAndFid(CodArt);
		if (promo != null) {
			if (promo.getTipoPromo().getIdTipoPromo() == 1) {
				try {
					retVal = Double.parseDouble(promo.getOggetto().replace(",", "."));
				} catch(NumberFormatException ex) {
					ex.printStackTrace();
				}
			} else {
				retVal = 0;
			}
		} else {
			System.out.println("Promo Articolo Fidelity Assente!");
		}
		return retVal;
	}
	
	@Override
	public Double SelByCodArtAndCodFid(String CodArt, String CodFid) {
		double retVal = 0;
		DettPromo promo =  prezziPromoRep.SelByCodArtAndCodFid(CodArt, CodFid);
		if (promo != null) {
			if (promo.getTipoPromo().getIdTipoPromo() == 1) {
				try {
					retVal = Double.parseDouble(promo.getOggetto().replace(",", "."));
				} catch(NumberFormatException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			System.out.println(String.format("Promo Riservata Fidelity %s Assente!!", CodFid) );
		}
		return retVal;
	}
	
	@Override
	public void UpdOggettoPromo(String Oggetto, Long Id) {
		// TODO Auto-generated method stub
	}
	
}
