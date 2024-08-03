package com.boot.lms.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
	private String token;
	private String userName;
	private Long userId;
}
