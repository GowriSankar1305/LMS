package com.boot.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.boot.lms.dto.ErrorResponseDto;

@RestControllerAdvice
public class LmsExceptionHandler {
	
	@ExceptionHandler(value = UserInputException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResponseDto handleInvalidInputException(UserInputException e)	{
		return new ErrorResponseDto(e.getMessage(), 400);
	}
}
