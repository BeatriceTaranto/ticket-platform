package com.ticketplatform.model.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ticketplatform.model.User;
import com.ticketplatform.model.repository.UserRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> userOpt = userRepository.findByUsername(username);

		if (userOpt.isPresent()) {
			DatabaseUserDetails userDetails = new DatabaseUserDetails(userOpt.get());
			return userDetails;
		} else {
			throw new UsernameNotFoundException("Username not found");
		}
	}

}
