package com.example.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Barcode;
import com.example.webapp.repository.BarcodeRepository;

@Service
@Transactional(readOnly = true)
public class BarcodeServiceImpl implements BarcodeService{

	@Autowired
	private BarcodeRepository barcodeRepositoy;
	
	@Override
	@Cacheable(value = "barcode", key = "#Barcode", sync = true)
	public Barcode getOne(String Barcode) {
		return barcodeRepositoy.findByBarcode(Barcode);
	}

}
