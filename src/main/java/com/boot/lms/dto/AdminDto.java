package com.boot.lms.dto;

import lombok.Data;

@Data
public class AdminDto {
	private Long adminId;
	private String firstName;
	private String lastname;
	private String mobileNo;
	private String emailId;
	private String userName;
	private String password;
}
