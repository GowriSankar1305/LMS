package com.boot.lms.dto;

import lombok.Data;

@Data
public class ErrorResponseDto {
	
	private String errorMessage;
	private Integer status;
	
	public ErrorResponseDto(String errorMessage,Integer status)	{
		this.errorMessage = errorMessage;
		this.status = status;
	}
}
