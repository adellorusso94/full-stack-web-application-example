package com.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableCaching
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class PriceartWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceartWebServiceApplication.class, args);
	}

}
