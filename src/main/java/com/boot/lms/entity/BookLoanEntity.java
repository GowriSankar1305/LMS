package com.boot.lms.entity;

import java.time.LocalDate;
import java.util.List;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.enums.BookLoanStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_book_loan",schema = AppConstants.DB_SCHEMA)
public class BookLoanEntity extends TimeStampEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "book_loa_id_gen")
	@SequenceGenerator(name = "book_loa_id_gen",allocationSize = 1,initialValue = 
	AppConstants.INITIAL_VALUE,schema = AppConstants.DB_SCHEMA,sequenceName = "seq_book_loan")
	private Long loanId;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_borrowed_books",schema = AppConstants.DB_SCHEMA,
		joinColumns = {@JoinColumn(name = "loan_id")},
		inverseJoinColumns =  {@JoinColumn(name = "book_id")})
	private List<BookEntity> books;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private MemberEntity member;
	private LocalDate issuedDate;
	private LocalDate returnedDate;
	@Enumerated(EnumType.STRING)
	private BookLoanStatusEnum loanStatus;
}	