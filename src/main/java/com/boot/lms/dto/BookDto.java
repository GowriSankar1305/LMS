package com.boot.lms.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookDto {
	private Long bookId;
	private String isbn;
	private String bookTitle;
	private PublishedDateDto publishedDate;
	private Integer noOfPages;
	private String bookDescription;
	private Boolean isAvailable;
	private Integer noOfAvailableCopies;
	private Long categoryId;
	private List<AuthorDto> authors;
	private String bookCategory;
}
