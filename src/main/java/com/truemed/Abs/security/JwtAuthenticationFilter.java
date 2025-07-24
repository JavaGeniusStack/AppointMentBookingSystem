package com.truemed.Abs.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String username = null;
		String jwt = request.getParameter("token");
		if (jwt != null) {
			log.info("EXTRACTED CLAIM IS" + jwtUtil.extractAllClaims(jwt).getSubject());

//			username = jwtUtil.extractUsername(jwt); // subject could be email or mobile
			username = jwtUtil.extractAllClaims(jwt).getSubject();
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			log.info("getting user");
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtUtil.isTokenValid(jwt, userDetails)) {
				log.info("token is valid and not expired");
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}
