package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.assertThat;
 
import com.example.webapp.ArticoliWebServiceApplication;
import com.example.webapp.entity.Barcode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ArticoliWebServiceApplication.class)
@SpringBootTest
public class BarcodeRepositoryTest
{
	@Autowired
	private BarcodeRepository barcodeRepository;
	
	
	@Test
	public void TestfindByBarcode() {
		assertThat(barcodeRepository.findByBarcode("8008490000021"))
		.extracting(Barcode::getBarcode)
		.isEqualTo("8008490000021");
	}
	
}