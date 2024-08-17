package com.boot.lms.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.BookCategoryDto;
import com.boot.lms.dto.BookDto;
import com.boot.lms.dto.BookLoanDto;
import com.boot.lms.dto.BookReservationDto;
import com.boot.lms.dto.EMailMessageDto;
import com.boot.lms.dto.FineDto;
import com.boot.lms.dto.MemberDto;
import com.boot.lms.dto.MembershipDto;
import com.boot.lms.dto.MembershipTypeDto;
import com.boot.lms.service.AdminService;
import com.boot.lms.service.BookCategoryService;
import com.boot.lms.service.BookLoanService;
import com.boot.lms.service.BookReservationService;
import com.boot.lms.service.BookService;
import com.boot.lms.service.FineService;
import com.boot.lms.service.MemberService;
import com.boot.lms.service.MembershipService;
import com.boot.lms.util.LmsUtility;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/")
@AllArgsConstructor
public class AdminController {

	private final BookService bookService;
	private final BookLoanService bookLoanService;
	private final BookReservationService bookReservationService;
	private final FineService fineService;
	private final MembershipService membershipService;
	private final AdminService adminService;
	private final LmsUtility lmsUtility;
	private final BookCategoryService bookCategoryService;
	private final MemberService memberService;
	
	@PostMapping("book/category/add")
	public ApiResponseDto addBookCategory(@RequestBody @Valid BookCategoryDto bookCategoryDto)	{
		return bookCategoryService.addBookCategory(bookCategoryDto.getCategoryName().toUpperCase());
	}
	
	@PostMapping("book/categories/find")
	public List<BookCategoryDto> getBookCategories()	{
		return bookCategoryService.fetchBookCategories();
	}
	
	@PostMapping("books/addBook")
	public ApiResponseDto addBookToLibrary(@RequestBody @Valid BookDto bookDto)	{
		return bookService.addBook(bookDto);
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
	
	@PostMapping("books/loan")
	public ApiResponseDto loanBooksToMember(@RequestBody @Valid BookLoanDto bookLoanDto)	{
		lmsUtility.refreshMemershipStatus(bookLoanDto.getMemberId());
		return bookLoanService.issueBooksLoan(bookLoanDto);
	}
	
	@PostMapping("books/loan/find")
	public BookLoanDto findBookLoanDetails(@RequestParam(required =  true) Long loanId)	{
		return bookLoanService.fetchBookLoanDeatils(loanId);
	}
	
	@PostMapping("books/member/loans")
	public List<BookLoanDto> findMemberBookLoans(@RequestParam(required =  true) Long memberId)	{
		return bookLoanService.fetchBookLoansOfAMember(memberId);
	}
	
	@PostMapping("books/reservation/find")
	public BookReservationDto findBookReservationDetails(@RequestParam(required = true) Long reservationId)	{
		return bookReservationService.fetchBookReservationDetails(reservationId);
	}
	
	@PostMapping("books/member/reservation/find")
	public List<BookReservationDto> findMemberBookReservations(@RequestParam(required = true) Long memberId)	{
		return bookReservationService.fetchBookReservationsOfMember(memberId);
	}
	
	@PostMapping("books/reservations")
	public List<BookReservationDto> findBookReservations()	{
		return bookReservationService.fetchBookReservations();
	}
	
	@PostMapping("member/fine")
	public ApiResponseDto addFine(@RequestBody @Validated FineDto fineDto)	{
		return fineService.addFine(fineDto);
	}
	
	@PostMapping("member/fine/find")
	public FineDto findFineDetails(@RequestParam(required = true) Long fineId)	{
		return fineService.fetchFineDetails(fineId);
	}
	
	@PostMapping("member/fines/find")
	public List<FineDto> findMemberFines(@RequestParam(required = true) Long memberId)	{
		return fineService.fetchAllFinesOfAMember(memberId);
	}
	
	@PostMapping("library/membershipType")
	public ApiResponseDto addMembershipType(@RequestBody @Valid MembershipTypeDto membershipTypeDto)	{
		log.info(" ---------- adding new mbsp type ------------------");
		return membershipService.createMemberShipType(membershipTypeDto);
	}
	
	@PostMapping("library/membershipTypes")
	public List<MembershipTypeDto> fetchMembershipTypes()	{
		return membershipService.fetchAllMembershipTypes();
	}
	
	@PostMapping("library/member/membership")
	public ApiResponseDto addLibraryMembership(@RequestBody @Valid MembershipDto membershipDto)	{
		return membershipService.addLibraryMembership(membershipDto);
	}
	
	@PostMapping("library/member/add")
	public ApiResponseDto addMemberToLibrary(@RequestBody @Valid MemberDto memberDto)	{
		return memberService.saveOrUpdateUser(memberDto);
	}
	
	@PostMapping("library/member/find")
	public MemberDto findMemberById(@RequestParam Long memberId)	{
		return memberService.fetchMemberById(memberId);
	}
	
	@PostMapping("library/members/find")
	public List<MemberDto> findMembersOfLibrary(@RequestParam Boolean status)	{
		return memberService.fetchMembers(status);
	}
	
	@PostMapping("member/membership/find")
	public MembershipDto findMembership(@RequestParam(required = true) Long membershipId)	{
		return membershipService.fetchMembershipDetails(membershipId);
	}
	
	@PostMapping("email/send")
	public ApiResponseDto sendEmailMessage(@RequestBody @Valid EMailMessageDto emailmsgDto) {
		return adminService.sendEmailToMember(emailmsgDto);
	}
}
