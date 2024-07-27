package com.boot.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.lms.entity.BookLoanEntity;
import com.boot.lms.enums.BookLoanStatusEnum;

import java.util.List;


public interface BookLoanEntityRepository extends JpaRepository<BookLoanEntity, Long> {
	List<BookLoanEntity> findByMember_MemberIdAndLoanStatus(Long memberId,BookLoanStatusEnum loanStatus);
	List<BookLoanEntity> findByMember_MemberId(Long memberId);
	BookLoanEntity findByLoanId(Long loanId);
}
