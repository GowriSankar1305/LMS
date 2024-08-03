package com.boot.lms.service;

import com.boot.lms.dto.AppUserDto;

public interface AppUserService {

	public AppUserDto findUserByUsername(String username);
}
