package com.boot.lms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName(value = "book")
public class BookDto {
	private Long bookId;
	@NotBlank
	private String isbn;
	@NotBlank
	private String bookTitle;
	@Valid
	private PublishedDateDto publishedDate;
	@Min(value = 50)
	private Integer noOfPages;
	@NotBlank
	private String bookDescription;
	@NotNull
	private Boolean isAvailable;
	@Min(value = 1)
	private Integer noOfAvailableCopies;
	@NotNull
	private Long categoryId;
	@NotEmpty
	@JsonProperty(value = "bookAuthors")
	private List<@Valid AuthorDto> authors;
	private String bookCategory;
	@NotBlank
	private String bookPrice;
}
