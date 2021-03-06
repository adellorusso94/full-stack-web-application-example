package com.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServerJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerJwtApplication.class, args);
	}

}
