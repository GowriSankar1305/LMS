package com.boot.lms.service;

import java.util.List;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.BookLoanDto;

public interface BookLoanService {
	public ApiResponseDto issueBooksLoan(BookLoanDto bookLoanDto);
	public BookLoanDto fetchBookLoanDeatils(Long loanId);
	public List<BookLoanDto> fetchBookLoansOfAMember(Long memberId);
}
