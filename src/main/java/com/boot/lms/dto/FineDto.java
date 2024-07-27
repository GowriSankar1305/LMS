package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FineDto {
	
	private Long fineId;
	private Long memberId;
	private String fineType;
	private String amount;
	private String paymentType;
	private PaymentDateDto paymentDateDto;
	private Long loanId;
}
