package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUserDto {
	private String userName;
	private String userPassword;
	private Long appUserId;
	private String role;
}
