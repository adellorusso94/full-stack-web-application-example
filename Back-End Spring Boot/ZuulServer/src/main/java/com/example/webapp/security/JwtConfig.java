package com.example.webapp.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("sicurezza")
public class JwtConfig {
	
	private String uri;
	private String header;
	private String prefix;
	private int expiration;
	private String secret;
	
	public JwtConfig() {
		
	}

	public JwtConfig(String uri, String header, String prefix, int expiration, String secret) {
		this.uri = uri;
		this.header = header;
		this.prefix = prefix;
		this.expiration = expiration;
		this.secret = secret;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
