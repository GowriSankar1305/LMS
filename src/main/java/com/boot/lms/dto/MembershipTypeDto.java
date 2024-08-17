package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MembershipTypeDto {
	private Long membershipTypeId;
	@NotBlank
	private String membershipType;
	@Min(value = 1)
	private Short timelineLimit;
	@Min(value = 1)
	private Short borrowingLimit;
	@NotBlank
	private String cost;
	@NotBlank
	private String membershipTimeline;
}
