package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDto {
	private Long memberId;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@Size(min = 10,max = 10)
	@Pattern(regexp = "[0-9]")
	private String mobileNo;
	@Email
	private String emailId;
	@NotBlank
	private String userName;
	@NotBlank
	@Size(min = 5,max = 20)
	private String password;
	@Valid
	private AddressDto address;
	@NotNull
	private Boolean isActive;
}
