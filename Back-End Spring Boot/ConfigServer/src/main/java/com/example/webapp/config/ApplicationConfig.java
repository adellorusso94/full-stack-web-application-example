package com.example.webapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("sicurezza")
public class ApplicationConfig {
	
	private String userUsername;
	private String userPassword;
	private String adminUsername;
	private String adminPassword;
	
	public ApplicationConfig() {
		
	}

	public ApplicationConfig(String userUsername, String userPassword, String adminUsername, String adminPassword) {
		this.userUsername = userUsername;
		this.userPassword = userPassword;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
	}

	public String getUserUsername() {
		return userUsername;
	}

	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

}
