package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {
	private Long addressId;
	@NotBlank
	private String landmark;
	@NotBlank
	private String city;
	@NotBlank
	private String state;
	@NotBlank
	private String pincode;
	@NotBlank
	private String addressLine;
}
