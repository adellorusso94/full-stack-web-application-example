package com.example.webapp.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.webapp.security.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	private final JwtConfig jwtConfig;
	
	public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String requestTokenHeader = request.getHeader(jwtConfig.getHeader());
		if (requestTokenHeader != null && requestTokenHeader.startsWith(jwtConfig.getPrefix())) {
			String jwtToken = requestTokenHeader.replace(jwtConfig.getPrefix(), "");
			try {
				Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(jwtToken).getBody();
				String username = claims.getSubject();
				if (username != null) {
					List<String> authorities = (List<String>) claims.get("authorities");
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, 
							null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			} catch(Exception e) {
				SecurityContextHolder.clearContext();
			}
		}
		chain.doFilter(request, response);
	}

}