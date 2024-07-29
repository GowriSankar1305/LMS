package com.boot.lms.service;

import com.boot.lms.dto.AdminDto;
import com.boot.lms.dto.ApiResponseDto;

public interface AdminService {
	public ApiResponseDto saveAdmin(AdminDto adminDto);
}
