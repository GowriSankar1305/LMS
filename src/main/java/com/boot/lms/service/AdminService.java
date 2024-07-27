package com.boot.lms.service;

import com.boot.lms.dto.AdminDto;
import com.boot.lms.dto.ApiResponse;

public interface AdminService {
	public ApiResponse saveAdmin(AdminDto adminDto);
}
