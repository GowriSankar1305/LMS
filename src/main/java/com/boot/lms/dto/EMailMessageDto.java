package com.boot.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EMailMessageDto {
	
	private Long messageId;
	@NotBlank
	private String subject;
	@NotBlank
	private String messageBody;
	private Long emailReceiverId;
	private Long emailSenderId;
}
