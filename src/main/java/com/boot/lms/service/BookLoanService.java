package com.boot.lms.service;

import java.util.List;

import com.boot.lms.dto.ApiResponse;
import com.boot.lms.dto.BookLoanDto;

public interface BookLoanService {
	public ApiResponse issueBooksLoan(BookLoanDto bookLoanDto);
	public BookLoanDto fetchBookLoanDeatils(Long loanId);
	public List<BookLoanDto> fetchBookLoansOfAMember(Long memberId);
}
