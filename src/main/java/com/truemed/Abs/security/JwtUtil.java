package com.truemed.Abs.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.truemed.Abs.Dto.Roles;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

	
	private final String SECRET_KEY = "mysecretkey123";


	public String generateTokenFromMail(String email, Roles role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", email);
		claims.put("role", role != null ? role.name() : "USER");
		return buildToken(email, claims);
	}

	public String generateTokenFromMobile(String mobile, Roles role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("mobile", mobile);
		claims.put("role", role != null ? role.name() : "USER");
		return buildToken(mobile, claims);
	}

	private String buildToken(String subject, Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public String extractEmail(String token) {
		return extractAllClaims(token).get("email", String.class);
	}

	public String extractMobile(String token) {
		return extractAllClaims(token).get("mobile", String.class);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		log.info("checking validity of token-->");
		final String username = extractUsername(token);
		log.info("Extracted username from token {} and from userdetails is {}",username, userDetails.getUsername());
		
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		log.info("checking is token expired");
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration()
				.before(new Date());
	}
}
