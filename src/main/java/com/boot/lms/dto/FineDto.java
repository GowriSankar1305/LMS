package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FineDto {
	
	private Long fineId;
	@Min(value = 1)
	private Long memberId;
	@NotBlank
	private String fineType;
	@NotBlank
	private String amount;
	@NotBlank
	private String paymentType;
	@Valid
	private PaymentDateDto paymentDateDto;
	@Min(value = 1)
	private Long loanId;
}
