package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName(value = "bookAuthors")
public class AuthorDto {
	private Long authorId;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	private String authorImage;
	private String authorBio;
}
