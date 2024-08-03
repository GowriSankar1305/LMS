package com.boot.lms.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.BookDto;
import com.boot.lms.dto.BookLoanDto;
import com.boot.lms.dto.IssuedDateDto;
import com.boot.lms.entity.BookEntity;
import com.boot.lms.entity.BookLoanEntity;
import com.boot.lms.entity.MembershipEntity;
import com.boot.lms.enums.BookLoanStatusEnum;
import com.boot.lms.enums.MembershipStatusEnum;
import com.boot.lms.exception.LmsException;
import com.boot.lms.exception.UserInputException;
import com.boot.lms.repository.BookEntityRepository;
import com.boot.lms.repository.BookLoanEntityRepository;
import com.boot.lms.repository.MemberEntityRepository;
import com.boot.lms.repository.MembershipEntityRepository;
import com.boot.lms.service.BookLoanService;
import com.boot.lms.util.ThreadLocalUtility;

@Service
public class BookLoanServiceImpl implements BookLoanService {

	@Autowired
	private BookLoanEntityRepository bookLoanEntityRepository;
	@Autowired
	private MembershipEntityRepository membershipEntityRepository;
	@Autowired
	private BookEntityRepository bookEntityRepository;
	@Autowired
	private MemberEntityRepository memberEntityRepository;

	@Override
	public ApiResponseDto issueBooksLoan(BookLoanDto bookLoanDto) {
		Long principalId = (Long) ThreadLocalUtility.get().get(AppConstants.PRINCIPAL_ID);
		checkCanMemberBorrowBooks(bookLoanDto.getMemberId());
		checkSelectedBooksAvailableToBorrow(bookLoanDto.getBooks());
		BookLoanEntity bookLoanEntity = new BookLoanEntity();
		bookLoanEntity.setCreatedBy(principalId);
		bookLoanEntity.setModifiedBy(principalId);
		bookLoanEntity.setCreatedTime(LocalDateTime.now());
		bookLoanEntity.setModifiedTime(LocalDateTime.now());
		bookLoanEntity.setLoanStatus(BookLoanStatusEnum.ISSUED);
		bookLoanEntity.setMember(memberEntityRepository.findByMemberId(bookLoanDto.getMemberId()));
		List<Long> bookIds = bookLoanDto.getBooks().stream().map(book -> book.getBookId()).collect(Collectors.toList());
		List<BookEntity> bookEntities = bookEntityRepository.findBooksByBookIds(bookIds);
		bookLoanEntity.setBooks(bookEntities);
		bookLoanEntityRepository.save(bookLoanEntity);
		bookEntities.stream()
				.peek(bookEntity -> bookEntity.setNoOfAvailableCopies(bookEntity.getNoOfAvailableCopies() - 1));
		bookEntityRepository.saveAll(bookEntities);
		return new ApiResponseDto("Book(s) issued successfully!", 200);
	}

	@Override
	public BookLoanDto fetchBookLoanDeatils(Long loanId) {
		BookLoanEntity bookLoanEntity = bookLoanEntityRepository.findByLoanId(loanId);
		if (Objects.isNull(bookLoanEntity)) {
			throw new UserInputException("Invalid book loan id!");
		}
		return mapToBkLnDto.apply(bookLoanEntity);
	}

	@Override
	public List<BookLoanDto> fetchBookLoansOfAMember(Long memberId) {
		List<BookLoanDto> bookLoanDtos = null;
		List<BookLoanEntity> bookLoanEntities = bookLoanEntityRepository.findByMember_MemberId(memberId);
		if (!CollectionUtils.isEmpty(bookLoanEntities)) {
			bookLoanDtos = bookLoanEntities.stream().map(mapToBkLnDto).collect(Collectors.toList());
		}
		return bookLoanDtos;
	}

	private Function<BookLoanEntity, BookLoanDto> mapToBkLnDto = (entity) -> {
		BookLoanDto dto = new BookLoanDto();
		dto.setLoanId(entity.getLoanId());
		IssuedDateDto issuedDateDto = new IssuedDateDto();
		issuedDateDto.setDay(entity.getIssuedDate().getDayOfMonth());
		issuedDateDto.setMonth(entity.getIssuedDate().getMonthValue());
		issuedDateDto.setYear(entity.getIssuedDate().getYear());
		dto.setIssuedDate(issuedDateDto);
		dto.setLoanStatus(entity.getLoanStatus().name());
		dto.setMemberId(null);
		return dto;
	};

	private void checkCanMemberBorrowBooks(Long memberId) {
		MembershipEntity membershipEntity = membershipEntityRepository
				.findByMember_MemberIdAndMembershipStatus(memberId, MembershipStatusEnum.ACTIVE);
		Short borrowingLimit = membershipEntity.getMembershipType().getBorrowingLimit();
		List<BookLoanEntity> nonReturnedBooks = bookLoanEntityRepository.findByMember_MemberIdAndLoanStatus(memberId,
				BookLoanStatusEnum.ISSUED);
		if (!CollectionUtils.isEmpty(nonReturnedBooks)) {
			List<List<BookEntity>> entityList = nonReturnedBooks.stream().map(loan -> loan.getBooks())
					.collect(Collectors.toList());
			List<BookEntity> issuedBooks = entityList.stream().flatMap(bookList -> bookList.stream())
					.collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(issuedBooks) && issuedBooks.size() >= borrowingLimit) {
				throw new LmsException("Member borrowing limit reached!");
			}
		}
	}

	private void checkSelectedBooksAvailableToBorrow(List<BookDto> books) {
		List<Long> bookIds = books.stream().map(book -> book.getBookId()).collect(Collectors.toList());
		List<BookEntity> bookEntities = bookEntityRepository.findBooksByBookIds(bookIds);
		if (CollectionUtils.isEmpty(bookEntities)) {
			throw new UserInputException("no books found with the given book ids!");
		}
		if (bookEntities.size() != bookIds.size()) {
			throw new UserInputException("few invalid book ids are received!");
		}
		books.stream().forEach(bookDto -> {
			BookEntity bookEntity = bookEntities.stream().filter(entity -> entity.getBookId() == bookDto.getBookId())
					.findAny().get();
			if (bookEntity.getNoOfAvailableCopies() == 0) {
				throw new LmsException("No copies available for book " + bookEntity.getBookId());
			}
		});
	}
}
