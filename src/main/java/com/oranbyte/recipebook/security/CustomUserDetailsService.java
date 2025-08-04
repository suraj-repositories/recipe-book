package com.oranbyte.recipebook.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.repository.UserRepository;

/**
 * Custom implementation of {@link UserDetailsService} for loading user details
 * by email.
 *
 * This service retrieves user information from {@link UserRepository} and
 * constructs a
 * {@link org.springframework.security.core.userdetails.UserDetails} object
 * required by Spring Security for authentication and authorization.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	/**
	 * Constructs a new {@code CustomUserDetailsService} with the specified
	 * {@link UserRepository}.
	 *
	 * @param userRepository The repository used to retrieve user information.
	 */
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Loads user details by their email address.
	 *
	 * @param email The email address of the user.
	 * @return A {@link UserDetails} object representing the authenticated user.
	 * @throws UsernameNotFoundException If no user with the specified email is
	 *                                   found.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElse(null);

		if (user != null) {
			String authority = "ROLE_" + user.getRole();
			return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority(authority))
			);
		} else {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
	}


}
