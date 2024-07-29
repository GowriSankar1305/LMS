package com.boot.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class DateDto {
	@Min(value = 1900)
	private int year;
	@Min(value = 1)
	@Max(value = 12)
	private int month;
	@Min(value = 1)
	@Max(value = 31)
	private int day;
}
