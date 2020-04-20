package com.example.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp.entity.Barcode;

public interface BarcodeRepository extends JpaRepository<Barcode,String> {
	Barcode findByBarcode(String barcode);
}
