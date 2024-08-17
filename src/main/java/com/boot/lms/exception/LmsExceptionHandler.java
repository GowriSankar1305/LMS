package com.boot.lms.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.boot.lms.dto.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class LmsExceptionHandler {
	
	@ExceptionHandler(value = UserInputException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResponseDto handleInvalidInputException(UserInputException e)	{
		return new ErrorResponseDto(e.getMessage(), 400);
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException me)	{
		log.error("validation failed-----> {}",me);
		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put("errors", me.getFieldErrors().stream()
				.map(mapToErrorMsg).collect(Collectors.toList()));
		return errorMap;
	}
	
	private Function<FieldError, String> mapToErrorMsg = (error) -> {
		return error.getField() + " " + error.getDefaultMessage();
	};
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, String> handleRunTimeException(RuntimeException re)	{
		log.error("exception------> {}",re);
		log.error(re.getMessage());
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("error", "Unable to process your request. Problem at our end!");
		return errorMap;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, String> handleException(Exception e)	{
		log.error(e.getMessage());
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("error", "Unable to process your request. Some system error has occured!");
		return errorMap;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Map<String, Object> handleRequestParamError(MissingServletRequestParameterException ex)	{
		log.error(ex.getMessage());
		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put("error", "Invalid request data!");
		return errorMap;
	}
}
