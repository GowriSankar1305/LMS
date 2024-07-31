package com.boot.lms.service;

import com.boot.lms.dto.AdminDto;
import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.EMailMessageDto;

public interface AdminService {
	public ApiResponseDto saveAdmin(AdminDto adminDto);
	public ApiResponseDto sendEmailToMember(EMailMessageDto emailMessageDto);
}
