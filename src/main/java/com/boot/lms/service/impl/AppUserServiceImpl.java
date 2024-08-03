package com.boot.lms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.lms.dto.AppUserDto;
import com.boot.lms.entity.AppUserEntity;
import com.boot.lms.repository.AppUserEntityRepository;
import com.boot.lms.service.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {
	
	@Autowired
	private AppUserEntityRepository appUserEntityRepository;
	
	@Override
	public AppUserDto findUserByUsername(String username) {
		AppUserEntity entity = appUserEntityRepository.findByUserName(username);
		AppUserDto appUserDto = new AppUserDto();
		appUserDto.setUserName(entity.getUserName());
		appUserDto.setRole(entity.getUserRole().name());
		appUserDto.setAppUserId(entity.getAppUserId());
		return appUserDto;
	}

}
