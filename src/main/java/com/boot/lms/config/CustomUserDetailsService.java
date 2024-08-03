package com.boot.lms.config;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.boot.lms.dto.AppUserDto;
import com.boot.lms.entity.AppUserEntity;
import com.boot.lms.repository.AppUserEntityRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AppUserEntityRepository appUserEntityRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUserEntity appUserEntity = appUserEntityRepository.findByUserName(username);
		if(Objects.isNull(appUserEntity))	{
			throw new UsernameNotFoundException("User not found!");
		}
		AppUserDto appUserDto = new AppUserDto();
		appUserDto.setUserName(appUserEntity.getUserName());
		appUserDto.setUserPassword(appUserEntity.getPassword());
		appUserDto.setRole(appUserEntity.getUserRole().name());
		appUserDto.setAppUserId(appUserEntity.getAppUserId());
		return new CustomUserDetails(appUserDto);
	}

}
