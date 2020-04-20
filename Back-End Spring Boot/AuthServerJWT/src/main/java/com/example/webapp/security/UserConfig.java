package com.example.webapp.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("gestuser")
public class UserConfig {
	
	private String srvUrl;
	private String username;
	private String password;
	
	public UserConfig() {
		
	}

	public UserConfig(String srvUrl, String username, String password) {
		this.srvUrl = srvUrl;
		this.username = username;
		this.password = password;
	}

	public String getSrvUrl() {
		return srvUrl;
	}

	public void setSrvUrl(String srvUrl) {
		this.srvUrl = srvUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}