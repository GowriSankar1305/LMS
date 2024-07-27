package com.boot.lms.dto;

import lombok.Data;

@Data
public class AuthorDto {
	private Long authorId;
	private Long firstName;
	private Long lastName;
	private String authorImage;
}
