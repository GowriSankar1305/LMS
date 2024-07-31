package com.boot.lms.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.EMailMessageDto;
import com.boot.lms.entity.EmailMessageEntity;
import com.boot.lms.exception.UserInputException;
import com.boot.lms.repository.EmailEntityRepository;
import com.boot.lms.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailEntityRepository emailEntityRepository;
	
	@Override
	public ApiResponseDto sendEmail(EMailMessageDto eMailMessageDto) {
		EmailMessageEntity emailMessageEntity = new EmailMessageEntity();
		emailMessageEntity.setEmailReceiverId(eMailMessageDto.getEmailReceiverId());
		emailMessageEntity.setEmailSenderId(eMailMessageDto.getEmailSenderId());
		emailMessageEntity.setEmailSentTime(LocalDateTime.now());
		emailMessageEntity.setIsEmailRead(Boolean.FALSE);
		emailMessageEntity.setSubject(eMailMessageDto.getSubject());
		emailMessageEntity.setMessageBody(eMailMessageDto.getMessageBody());
		 emailEntityRepository.save(emailMessageEntity);
		return null;
	}

	@Override
	public EMailMessageDto fetchEmailMessage(Long messageId) {
		EmailMessageEntity emailMessageEntity = emailEntityRepository.findByMessageId(messageId);
		if(Objects.isNull(emailMessageEntity))	{
			throw new UserInputException("Invalid email message id!");
		}
		return mapToDto.apply(emailMessageEntity);
	}

	private Function<EmailMessageEntity, EMailMessageDto> mapToDto = entity -> {
		EMailMessageDto dto = new EMailMessageDto();
		dto.setEmailReceiverId(entity.getEmailReceiverId());
		dto.setEmailSenderId(entity.getEmailSenderId());
		dto.setMessageBody(entity.getMessageBody());
		dto.setSubject(entity.getSubject());
		dto.setMessageId(entity.getMessageId());
		return dto;
	};
	
}
