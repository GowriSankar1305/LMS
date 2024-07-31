package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MembershipDto {
	private Long membershipId;
	@NotNull
	private Boolean isMembershipActive;
	@Valid
	private FromDateDto fromDate;
	@Valid
	private ToDateDto toDate;
	@NotBlank
	private String amountPaid;
	@Min(value = 1)
	private Long memberShipTypeId;
	private String memberShipType;
	@Min(value = 1)
	private Long memberId;
	@NotBlank
	private String paymentType;
	private String membershipStatus;
}
