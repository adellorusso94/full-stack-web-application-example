package com.example.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.webapp.config.ApplicationConfig;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ApplicationConfig appConfig;
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserBuilder users = User.builder();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(users
				.username(appConfig.getUserUsername())
				.password(new BCryptPasswordEncoder().encode(appConfig.getUserPassword()))
				.roles("USER").build());
		manager.createUser(users
				.username(appConfig.getAdminUsername())
				.password(new BCryptPasswordEncoder().encode(appConfig.getAdminPassword()))
				.roles("USER", "ADMIN").build());
		return manager;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.httpBasic();
	}
	
}
