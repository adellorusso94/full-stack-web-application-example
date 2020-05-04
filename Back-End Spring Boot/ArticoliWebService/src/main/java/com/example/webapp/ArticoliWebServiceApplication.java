package com.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@SpringBootApplication
@EnableFeignClients("com.example.webapp.feign")
@EnableEurekaClient
@EnableHystrix
public class ArticoliWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticoliWebServiceApplication.class, args);
	}

}
