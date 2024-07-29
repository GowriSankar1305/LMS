package com.boot.lms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookLoanDto {

	private Long loanId;
	@NotEmpty
	private List<@Valid BookDto> books;
	@NotNull
	private Long memberId;
	@Valid
	private IssuedDateDto issuedDate;
	@Valid
	private ReturnedDateDto returnedDate;
	private String loanStatus;
}
