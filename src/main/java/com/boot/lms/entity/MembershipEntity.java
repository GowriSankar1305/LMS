package com.boot.lms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.enums.MembershipStatusEnum;
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
@Table(name = "tbl_membership",schema = AppConstants.DB_SCHEMA)
public class MembershipEntity extends TimeStampEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "mbrsp_id_gen")
	@SequenceGenerator(name = "mbrsp_id_gen",initialValue = AppConstants.INITIAL_VALUE,
	allocationSize = 1,schema = AppConstants.DB_SCHEMA,sequenceName = "seq_membership")
	private Long membershipId;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "membership_type_id")
	private MembershipTypeEntity membershipType;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private MemberEntity member;
	private Boolean isMembershipActive;
	private LocalDate fromDate;
	private LocalDate toDate;
	private BigDecimal amountPaid;
	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType;
	@Enumerated(EnumType.STRING)
	private MembershipStatusEnum membershipStatus;
}
