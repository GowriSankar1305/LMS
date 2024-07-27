package com.boot.lms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.enums.FineTypeEnum;
import com.boot.lms.enums.PaymentTypeEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_fine",schema = AppConstants.DB_SCHEMA)
public class FineEntity extends TimeStampEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "fine_id_gen")
	@SequenceGenerator(name = "fine_id_gen",allocationSize = 1,initialValue = 
	AppConstants.INITIAL_VALUE,schema = AppConstants.DB_SCHEMA,sequenceName = "seq_fine")
	private Long fineId;
	@OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private MemberEntity member;
	@Enumerated(EnumType.STRING)
	private FineTypeEnum fineType;
	private BigDecimal amount;
	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType;
	private LocalDate paymentDate;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_id")
	private BookLoanEntity bookLoan;
}
