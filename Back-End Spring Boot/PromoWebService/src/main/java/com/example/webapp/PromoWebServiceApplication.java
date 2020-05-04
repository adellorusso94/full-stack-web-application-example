package com.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.example.webapp.feign")
@EnableHystrix
public class PromoWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromoWebServiceApplication.class, args);
	}

}
