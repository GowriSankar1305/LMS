package com.boot.lms.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.AuthorDto;
import com.boot.lms.dto.BookDto;
import com.boot.lms.dto.PublishedDateDto;
import com.boot.lms.entity.AuthorEntity;
import com.boot.lms.entity.BookEntity;
import com.boot.lms.exception.UserInputException;
import com.boot.lms.repository.BookCategoryEntiyRepository;
import com.boot.lms.repository.BookEntityRepository;
import com.boot.lms.service.BookService;
import com.boot.lms.util.LmsUtility;
import com.boot.lms.util.ThreadLocalUtility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookCategoryEntiyRepository bookCategoryEntiyRepository;
	@Autowired
	private BookEntityRepository bookEntityRepository;
	
	@Override
	@Transactional
	public ApiResponseDto addBook(BookDto bookDto) {
		log.info("book request data-------> {}",bookDto);
		if(Objects.isNull(bookDto))	{
			throw new UserInputException("Invalid book information received!");
		}
		Long principalId = (Long)ThreadLocalUtility.get().get(AppConstants.PRINCIPAL_ID);
		BookEntity bookEntity = new BookEntity();
		bookEntity.setBookCategory(bookCategoryEntiyRepository.findById(bookDto.getCategoryId()).get());
		log.info("published date-----> {}",bookDto.getPublishedDate());
		if(Objects.nonNull(bookDto.getPublishedDate()))	{
			PublishedDateDto date = bookDto.getPublishedDate();
			LocalDate publishedDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
			if(publishedDate.compareTo(LocalDate.now()) > 0)	{
				throw new UserInputException("Invalid published date!");
			}
			bookEntity.setPublishedDate(publishedDate);
		}
		if(!CollectionUtils.isEmpty(bookDto.getAuthors()))	{
			List<AuthorEntity> authorEntityList = new ArrayList<>();
			for(AuthorDto dto : bookDto.getAuthors())	{
				AuthorEntity authorEntity = new AuthorEntity();
				authorEntity.setCreatedBy(principalId);
				authorEntity.setModifiedBy(principalId);
				authorEntity.setFirstName(dto.getFirstName());
				authorEntity.setLastName(dto.getLastName());
				authorEntity.setAuthorBio(dto.getAuthorBio());
				authorEntity.setCreatedTime(LocalDateTime.now());
				authorEntity.setModifiedTime(LocalDateTime.now());
				authorEntityList.add(authorEntity);
			}
			bookEntity.setAuthors(authorEntityList);
		}
		bookEntity.setBookDescription(bookDto.getBookDescription());
		bookEntity.setBookTitle(bookDto.getBookTitle());
		bookEntity.setCreatedBy(principalId);
		bookEntity.setModifiedBy(principalId);
		bookEntity.setCreatedTime(LocalDateTime.now());
		bookEntity.setModifiedTime(LocalDateTime.now());
		bookEntity.setIsAvailable(bookDto.getIsAvailable());
		bookEntity.setIsbn(bookDto.getIsbn());
		bookEntity.setNoOfAvailableCopies(bookDto.getNoOfAvailableCopies());
		bookEntity.setNoOfPages(bookDto.getNoOfPages());
		bookEntity.setBookPrice(LmsUtility.parseAmount(bookDto.getBookPrice()));
		bookEntityRepository.save(bookEntity);
		return new ApiResponseDto("Book added successfully!", 200);
	}

	@Override
	@Transactional
	public BookDto fetchBookById(Long bookId) {
		BookDto bookDto = null;
		BookEntity bookEntity = bookEntityRepository.findByBookId(bookId);
		if(Objects.isNull(bookEntity))	{
			throw new UserInputException("Invalid book id!");
		}
		bookDto = mapToBookDto.apply(bookEntity);
		bookDto.setAuthors(bookEntity.getAuthors().stream()
				.map(mapToAuthorDto).collect(Collectors.toList()));
		return bookDto;
	}

	@Override
	public List<BookDto> fetchBooksByCategoryId(Long categoryId) {
		List<BookDto> bookDtos = null;
		List<BookEntity> bookEntities = bookEntityRepository.findByBookCategory_CategoryId(categoryId);
		if(!CollectionUtils.isEmpty(bookEntities))	{
			bookDtos = bookEntities.stream().map(mapToBookDto).collect(Collectors.toList());
		}
		return bookDtos;
	}

	@Override
	public List<BookDto> fetchAllBooks() {
		List<BookDto> bookDtos = null;
		List<BookEntity> bookEntities = bookEntityRepository.findAll();
		if(!CollectionUtils.isEmpty(bookEntities))	{
			bookDtos = bookEntities.stream().map(mapToBookDto).collect(Collectors.toList());
		}
		return bookDtos;
	}

	private Function<BookEntity, BookDto> mapToBookDto = (entity) -> {
		BookDto bookDto = new BookDto();
		bookDto.setBookDescription(entity.getBookDescription());
		bookDto.setBookId(entity.getBookId());
		bookDto.setBookTitle(entity.getBookTitle());
		bookDto.setBookCategory(entity.getBookCategory().getCategoryName());
		bookDto.setIsAvailable(entity.getIsAvailable());
		bookDto.setIsbn(entity.getIsbn());
		bookDto.setNoOfAvailableCopies(entity.getNoOfAvailableCopies());
		bookDto.setNoOfPages(entity.getNoOfPages());
		if(Objects.nonNull(entity.getPublishedDate()))	{
			PublishedDateDto publishedDateDto = new PublishedDateDto();
			publishedDateDto.setDay(entity.getPublishedDate().getDayOfMonth());
			publishedDateDto.setMonth(entity.getPublishedDate().getMonthValue());
			publishedDateDto.setYear(entity.getPublishedDate().getYear());
			bookDto.setPublishedDate(publishedDateDto);
		}
		bookDto.setBookPrice(entity.getBookPrice().toString());
		bookDto.setBookCategory(entity.getBookCategory().getCategoryName());
		bookDto.setCategoryId(entity.getBookCategory().getCategoryId());
		return bookDto;
	};
	
	private Function<AuthorEntity, AuthorDto> mapToAuthorDto = (entity) -> {
		AuthorDto dto = new AuthorDto();
		dto.setAuthorId(entity.getAuthorId());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setAuthorImage(entity.getAuthorImage());
		return dto;
	};
}
