package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDto {
	private Long memberId;
	private String firstName;
	private String lastName;
	private String mobileNo;
	private String emailId;
	private String userName;
	private String password;
	private AddressDto address;
	private Boolean isActive;
}
