package com.example.webapp.service;

import com.example.webapp.entity.Barcode;

public interface BarcodeService {
	
	public Barcode getOne(String barcode, String header);

}
