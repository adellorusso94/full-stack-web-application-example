package com.example.webapp.security;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserConfig userConfig;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(username == null || username.length() < 2) {
			String errMsg = "Username assente o non valido";
			throw new UsernameNotFoundException(errMsg);
		} else {
			Utenti utente = this.getHttpValue(username);
			if(utente == null) {
				String errMsg = "Utente " + username + " non trovato!";
				throw new UsernameNotFoundException(errMsg);
			} else {
				UserBuilder builder = User.withUsername(utente.getUsername());
				builder.disabled((utente.getAttivo().equals("Si") ? false : true));
				builder.password(utente.getPassword());
				String[] profili = utente.getRuoli().stream().map(a -> "ROLE_" + a).toArray(String[]::new);
				builder.authorities(profili);
				return builder.build();
			}
		}
	}
	
	private Utenti getHttpValue(String username) {
		URI url = null;
		try {
			String srvUrl = userConfig.getSrvUrl();
			url = new URI(srvUrl + username);
		} catch(URISyntaxException e) {
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(userConfig.getUsername(), userConfig.getPassword()));
		Utenti utente = null;
		try {
			utente = restTemplate.getForObject(url, Utenti.class);
		} catch(Exception e) {
			System.out.println("Connessione al servizio di autenticazione non riuscita!");
		}
		return utente;
	}

}