package com.boot.lms.service;

import java.util.List;

import com.boot.lms.dto.ApiResponse;
import com.boot.lms.dto.BookDto;

public interface BookService {
	public ApiResponse addBook(BookDto bookDto);
	public BookDto fetchBookById(Long bookId);
	public List<BookDto> fetchBooksByCategoryId(Long categoryId);
	public List<BookDto> fetchAllBooks();
}
