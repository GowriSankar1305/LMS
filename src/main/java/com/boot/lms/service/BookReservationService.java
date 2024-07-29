package com.boot.lms.service;

import java.util.List;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.BookReservationDto;

public interface BookReservationService {
	public ApiResponseDto reserveBooks(BookReservationDto bookReservationDto);
	public BookReservationDto fetchBookReservationDetails(Long reservationId);
	public List<BookReservationDto> fetchBookReservationsOfMember(Long member);
	public List<BookReservationDto> fetchBookReservations();
}
