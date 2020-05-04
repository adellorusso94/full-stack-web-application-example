package com.example.webapp.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.webapp.filter.JwtTokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtConfig jwtConfig;
	
	private static final String[] NOAUTH_MATCHER = { "/api/articoli/test/**", "/api/articoli/noauth/**", "/api/prezzi/noauth/**" };
	private static final String[] USER_MATCHER = { "/api/articoli/cerca/**", "/api/prezzi/**", "/api/prezzi/listino/cerca/**",
			"/api/promo/**", "/api/promo/prezzo/**"};
	private static final String[] ADMIN_MATCHER = { "/api/articoli/inserisci/**", "/api/articoli/modifica/**", "/api/articoli/elimina/**",
			"/api/prezzi/inserisci/**", "/api/prezzi/elimina/**", "/api/prezzi/listino/inserisci/**", "/api/prezzi/listino/elimina/**",
			"/api/promo/inserisci/**", "/api/promo/modifica/**", "/api/promo/elimina/**"};

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			.and()
			.addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
			.antMatchers(NOAUTH_MATCHER).permitAll()
			.antMatchers(USER_MATCHER).hasAnyRole("USER")
			.antMatchers(ADMIN_MATCHER).hasAnyRole("ADMIN")
			.anyRequest().authenticated();
	}
	
	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers(HttpMethod.POST, jwtConfig.getUri())
			.antMatchers(HttpMethod.OPTIONS, "/**")
			.and().ignoring()
			.antMatchers(HttpMethod.GET, "/");
	}
	
}