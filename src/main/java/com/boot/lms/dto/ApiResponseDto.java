package com.boot.lms.dto;

import lombok.Data;

@Data
public class ApiResponseDto {

	private String message;
	private int status;

	public ApiResponseDto(String message, int status) {
		this.message = message;
		this.status = status;
	}
	
	public ApiResponseDto() {
		
	}
}
