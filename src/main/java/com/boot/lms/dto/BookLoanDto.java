package com.boot.lms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookLoanDto {

	private Long loanId;
	private List<BookDto> books;
	private Long memberId;
	private IssuedDateDto issuedDate;
	private ReturnedDateDto returnedDate;
	private String loanStatus;
}
