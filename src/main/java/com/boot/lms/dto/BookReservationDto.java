package com.boot.lms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookReservationDto {
	private Long reservationId;
	private Long memberId;
	private ReservationDateDto reservationDate;
	private String reservationStatus;
	private List<BookDto> books;
}
