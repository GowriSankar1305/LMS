package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorDto {
	private Long authorId;
	@NotBlank
	private Long firstName;
	@NotBlank
	private Long lastName;
	private String authorImage;
}
