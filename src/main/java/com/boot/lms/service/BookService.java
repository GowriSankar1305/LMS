package com.boot.lms.service;

import java.util.List;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.BookDto;

public interface BookService {
	public ApiResponseDto addBook(BookDto bookDto);
	public BookDto fetchBookById(Long bookId);
	public List<BookDto> fetchBooksByCategoryId(Long categoryId);
	public List<BookDto> fetchAllBooks();
}
