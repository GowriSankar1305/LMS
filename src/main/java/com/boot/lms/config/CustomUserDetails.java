package com.boot.lms.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.boot.lms.dto.AppUserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3988398896528597038L;
	
	private final AppUserDto appUserDto;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(appUserDto.getRole()));
	}

	@Override
	public String getPassword() {
		return appUserDto.getUserPassword();
	}

	@Override
	public String getUsername() {
		return appUserDto.getUserName();
	}

}
