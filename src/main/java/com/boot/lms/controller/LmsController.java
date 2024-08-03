package com.boot.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.lms.dto.AppUserDto;
import com.boot.lms.dto.LoginRequestDto;
import com.boot.lms.dto.LoginResponseDto;
import com.boot.lms.service.AppUserService;
import com.boot.lms.util.JwtUtility;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/lms/")
public class LmsController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private JwtUtility jwtUtility;
	
	@PostMapping("validateUser")
	public LoginResponseDto loginTheUser(@RequestBody @Valid LoginRequestDto loginRequestDto)	throws Exception {
		log.info("***** authenticating user *****");
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(
						loginRequestDto.getUserName(), loginRequestDto.getUserPassword());
		Authentication authResponse = authenticationManager.authenticate(authentication);
		log.info("authorities-----> {}",authResponse.getAuthorities());
		log.info("name-----> {}",authResponse.getName());
		AppUserDto appUserDto = appUserService.findUserByUsername(authResponse.getName());
		String token = jwtUtility.generateToken(appUserDto.getUserName(), appUserDto.getAppUserId());
		
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setToken(token);
		loginResponseDto.setUserId(appUserDto.getAppUserId());
		loginResponseDto.setUserName(appUserDto.getUserName());
		return loginResponseDto;
	}
}
