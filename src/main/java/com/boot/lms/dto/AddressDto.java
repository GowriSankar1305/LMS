package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {
	private Long addressId;
	private String landmark;
	private String city;
	private String state;
	private String pincode;
	private String addressLine;
}
