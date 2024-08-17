package com.boot.lms.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.BookCategoryDto;
import com.boot.lms.entity.BookCategoryEntiy;
import com.boot.lms.exception.UserInputException;
import com.boot.lms.repository.BookCategoryEntiyRepository;
import com.boot.lms.service.BookCategoryService;
import com.boot.lms.util.ThreadLocalUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookCategoryServiceImpl implements BookCategoryService {

	private final BookCategoryEntiyRepository bookCategoryEntiyRepository;
	
	@Override
	public List<BookCategoryDto> fetchBookCategories() {
		List<BookCategoryDto> bookCategoryDtos = new ArrayList<>();
		List<BookCategoryEntiy> bookCategoryEntiys = bookCategoryEntiyRepository.findAll();
		if(!CollectionUtils.isEmpty(bookCategoryEntiys))	{
			bookCategoryEntiys.forEach(category -> {
				BookCategoryDto dto = new BookCategoryDto();
				dto.setCategoryName(category.getCategoryName());
				dto.setCategoryId(category.getCategoryId());
				bookCategoryDtos.add(dto);
			});
		}
		return bookCategoryDtos;
	}

	@Override
	public ApiResponseDto addBookCategory(String category) {
		Long principalId = (Long) ThreadLocalUtility.get().get(AppConstants.PRINCIPAL_ID);
		 if(Objects.nonNull(bookCategoryEntiyRepository.findByCategoryName(category)))	{
			 throw new UserInputException("Book category already exists!");
		 }
		 BookCategoryEntiy entity = new BookCategoryEntiy();
		 entity.setCategoryName(category);
		 entity.setCreatedBy(principalId);
		 entity.setModifiedBy(principalId);
		 entity.setCreatedTime(LocalDateTime.now());
		 entity.setModifiedTime(LocalDateTime.now());
		 bookCategoryEntiyRepository.save(entity);
		 return new ApiResponseDto("Category added successfully!", 200);
	}

}
