package com.boot.lms.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.BookReservationDto;
import com.boot.lms.dto.DateDto;
import com.boot.lms.dto.ReservationDateDto;
import com.boot.lms.entity.BookEntity;
import com.boot.lms.entity.BookReservationEntity;
import com.boot.lms.enums.ReservationStatusEnum;
import com.boot.lms.repository.BookEntityRepository;
import com.boot.lms.repository.BookReservationEntityRepository;
import com.boot.lms.repository.MemberEntityRepository;
import com.boot.lms.service.BookReservationService;
import com.boot.lms.util.ThreadLocalUtility;

@Service
public class BookReservationServiceImpl implements BookReservationService {

	@Autowired
	private BookReservationEntityRepository bookReservationEntityRepository;
	@Autowired
	private MemberEntityRepository memberEntityRepository;
	@Autowired
	private BookEntityRepository bookEntityRepository;

	@Override
	public ApiResponseDto reserveBooks(BookReservationDto bookReservationDto) {
		Long principalId = (Long) ThreadLocalUtility.get().get(AppConstants.PRINCIPAL_ID);
		BookReservationEntity bookReservationEntity = new BookReservationEntity();
		bookReservationEntity.setCreatedBy(principalId);
		bookReservationEntity.setModifiedBy(principalId);
		bookReservationEntity.setCreatedTime(LocalDateTime.now());
		bookReservationEntity.setModifiedTime(LocalDateTime.now());
		DateDto reservationDate = bookReservationDto.getReservationDate();
		bookReservationEntity.setReservationDate(
				LocalDate.of(reservationDate.getYear(), reservationDate.getMonth(), reservationDate.getDay()));
		bookReservationEntity.setStatus(ReservationStatusEnum.valueOf(bookReservationDto.getReservationStatus()));
		bookReservationEntity.setMember(memberEntityRepository.findByMemberId(bookReservationDto.getMemberId()));
		if (!CollectionUtils.isEmpty(bookReservationDto.getBookIds())) {
			List<BookEntity> bookEntities = bookEntityRepository.findBooksByBookIds(bookReservationDto.getBookIds());
			bookReservationEntity.setBooks(bookEntities);
		}
		bookReservationEntityRepository.save(bookReservationEntity);
		return new ApiResponseDto("Books reserved successfully!", 200);
	}

	@Override
	public BookReservationDto fetchBookReservationDetails(Long reservationId) {
		BookReservationEntity bookReservationEntity = bookReservationEntityRepository
				.findByReservationId(reservationId);
		return mapToBkRsrDto.apply(bookReservationEntity);
	}

	@Override
	public List<BookReservationDto> fetchBookReservationsOfMember(Long member) {
		List<BookReservationDto> bookReservationDtos = null;
		List<BookReservationEntity> bookReservationEntities = bookReservationEntityRepository
				.findByMember_MemberId(member);
		if (!CollectionUtils.isEmpty(bookReservationEntities)) {
			bookReservationDtos = bookReservationEntities.stream().map(mapToBkRsrDto).collect(Collectors.toList());
		}
		return bookReservationDtos;
	}

	@Override
	public List<BookReservationDto> fetchBookReservations() {
		List<BookReservationDto> bookReservationDtos = null;
		List<BookReservationEntity> bookReservationEntities = bookReservationEntityRepository.findAll();
		if (!CollectionUtils.isEmpty(bookReservationEntities)) {
			bookReservationDtos = bookReservationEntities.stream().map(mapToBkRsrDto).collect(Collectors.toList());
		}
		return bookReservationDtos;
	}

	Function<BookReservationEntity, BookReservationDto> mapToBkRsrDto = (entity) -> {
		BookReservationDto dto = new BookReservationDto();
		dto.setMemberId(entity.getMember().getMemberId());
		ReservationDateDto reservationDateDto = new ReservationDateDto();
		reservationDateDto.setDay(entity.getReservationDate().getDayOfMonth());
		reservationDateDto.setMonth(entity.getReservationDate().getMonthValue());
		reservationDateDto.setYear(entity.getReservationDate().getYear());
		dto.setReservationDate(reservationDateDto);
		dto.setReservationStatus(entity.getStatus().name());
		return dto;
	};
}
