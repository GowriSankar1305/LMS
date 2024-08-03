package com.boot.lms.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.BookDto;
import com.boot.lms.dto.BookLoanDto;
import com.boot.lms.dto.BookReservationDto;
import com.boot.lms.dto.EMailMessageDto;
import com.boot.lms.dto.FineDto;
import com.boot.lms.dto.MembershipDto;
import com.boot.lms.service.BookLoanService;
import com.boot.lms.service.BookReservationService;
import com.boot.lms.service.BookService;
import com.boot.lms.service.FineService;
import com.boot.lms.service.MemberService;
import com.boot.lms.service.MembershipService;
import com.boot.lms.util.LmsUtility;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/member/")
public class MemberController {

	private final BookReservationService bookReservationService;
	private final FineService fineService;
	private final MembershipService membershipService;
	private final BookService bookService;
	private final BookLoanService bookLoanService;
	private final MemberService memberService;
	private final LmsUtility lmsUtility;
	
	@PostMapping("books/reserve")
	public ApiResponseDto reserveBooks(@RequestBody @Valid BookReservationDto bookReservationDto)	{
		lmsUtility.refreshMemershipStatus(bookReservationDto.getMemberId());
		return bookReservationService.reserveBooks(bookReservationDto);
	}
	
	@PostMapping("books/reservation/find")
	public BookReservationDto findBookReservationDetails(@RequestParam(required = true) Long reservationId)	{
		return bookReservationService.fetchBookReservationDetails(reservationId);
	}
	
	@PostMapping("books/reservations/find")
	public List<BookReservationDto> findMemberBookReservations(@RequestParam(required = true) Long memberId)	{
		return bookReservationService.fetchBookReservationsOfMember(memberId);
	}
	
	@PostMapping("fine/find")
	public FineDto findFineDetails(@RequestParam(required = true) Long fineId)	{
		return fineService.fetchFineDetails(fineId);
	}
	
	@PostMapping("fines/find")
	public List<FineDto> findMemberFines(@RequestParam(required = true) Long memberId)	{
		return fineService.fetchAllFinesOfAMember(memberId);
	}
	
	@PostMapping("member/membership/find")
	public MembershipDto findMembership(@RequestParam(required = true) Long membershipId)	{
		return membershipService.fetchMembershipDetails(membershipId);
	}
	
	@PostMapping("books/find")
	public BookDto findBookDetails(@RequestParam(required = true) Long bookId) {
		return bookService.fetchBookById(bookId);
	}
	
	@PostMapping("books/category/find")
	public List<BookDto> findBooksByCategory(@RequestParam(required = true) Long categoryId) {
		return bookService.fetchBooksByCategoryId(categoryId);
	}
	
	@PostMapping("books/findAll")
	public List<BookDto> findBooks(){
		return bookService.fetchAllBooks();
	}

	@PostMapping("books/loan/find")
	public BookLoanDto findBookLoanDetails(@RequestParam(required =  true) Long loanId)	{
		return bookLoanService.fetchBookLoanDeatils(loanId);
	}
	
	@PostMapping("books/loans/find")
	public List<BookLoanDto> findMemberBookLoans(@RequestParam(required =  true) Long memberId)	{
		return bookLoanService.fetchBookLoansOfAMember(memberId);
	}
	
	@PostMapping("email/send")
	public ApiResponseDto sendEmail(@RequestBody @Valid EMailMessageDto eMailMessageDto)	{
		return memberService.sendEmailToAdmin(eMailMessageDto);
	}
}
