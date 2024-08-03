package com.boot.lms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookReservationDto {
	private Long reservationId;
	@NotNull
	private Long memberId;
	@Valid
	private ReservationDateDto reservationDate;
	private String reservationStatus;
	private List<@NotNull Long> bookIds;
}
