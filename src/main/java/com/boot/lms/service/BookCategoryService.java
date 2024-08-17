package com.boot.lms.service;

import java.util.List;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.BookCategoryDto;

public interface BookCategoryService {
	public List<BookCategoryDto> fetchBookCategories();
	public ApiResponseDto addBookCategory(String category);
}
