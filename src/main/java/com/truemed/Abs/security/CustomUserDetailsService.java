package com.truemed.Abs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truemed.Abs.Entity.CustomUser;
import com.truemed.Abs.Repo.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Load user by email or mobile for authentication.
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
		CustomUser user;

		
		// Check if identifier is email or mobile
		if (identifier.contains("@")) {
			log.info("identifier is mail"+identifier);
			user = userRepository.findByEmail(identifier)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + identifier));
		} else {
			log.info("identifier is mobile"+identifier);
			user = userRepository.findByMobile(identifier)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with mobile: " + identifier));
			log.info("User is :"+user.toString());
		}

		return new CustomUserDetails(user);
	}
}
