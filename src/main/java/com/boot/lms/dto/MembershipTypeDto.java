package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MembershipTypeDto {
	private Long membershipTypeId;
	private String membershipType;
	private Short timelineDays;
	private Short borrowingLimit;
	private String cost;
}
