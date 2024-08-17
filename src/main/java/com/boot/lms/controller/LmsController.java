package com.boot.lms.controller;

import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping("yearsAndMonths")
	public Map<String, List<?>> getYearsAndMonths()	{
		Map<String, List<?>> timelineMap = new HashMap<>();
		timelineMap.put("months", Stream.of(Month.values())
				.map(month -> month.name()).collect(Collectors.toList()));
		timelineMap.put("years", IntStream.range(1930, Year.now()
				.getValue() + 1).boxed().collect(Collectors.toList()));
		return timelineMap;
	}
	
	@PostMapping("fetchDays")
	public Map<String, List<Integer>> 
	populateDaysByMnth(@RequestParam(required = true) Integer month)	{
		Map<String, List<Integer>> daysMap = new HashMap<>();
		daysMap.put("days", IntStream.range(1, Month.of(month).length(Year.now().isLeap()) + 1)
				.boxed().collect(Collectors.toList()));
		return daysMap;
	}
}
