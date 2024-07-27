package com.boot.lms.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MembershipDto {
	private Long membershipId;
	private Boolean isMembershipActive;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String amountPaid;
	private Long memberShipTypeId;
	private String memberShipType;
	private Long memberId;
	private String paymentType;
}
