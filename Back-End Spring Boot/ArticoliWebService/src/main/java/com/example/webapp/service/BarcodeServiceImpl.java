package com.example.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webapp.entity.Barcode;
import com.example.webapp.repository.BarcodeRepository;

@Service
public class BarcodeServiceImpl implements BarcodeService{

	@Autowired
	private BarcodeRepository barcodeRepositoy;
	
	@Override
	public Barcode getOne(String Barcode) {
		return barcodeRepositoy.findByBarcode(Barcode);
	}

}
