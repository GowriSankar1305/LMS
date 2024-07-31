package com.boot.lms.service;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.EMailMessageDto;

public interface EmailService {
	public ApiResponseDto sendEmail(EMailMessageDto eMailMessageDto);
	public EMailMessageDto fetchEmailMessage(Long messageId);
}
