package com.ticketplatform.model.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ticketplatform.model.Role;
import com.ticketplatform.model.User;

public class DatabaseUserDetails implements UserDetails {

	private Long Id;
	private String username;
	private String password;
	private Set<GrantedAuthority> authorities;

	public DatabaseUserDetails(User user) {
		this.setId(user.getId());
		this.username = user.getUsername();
		this.password = user.getPassword();
		authorities = new HashSet<>();
		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
}
