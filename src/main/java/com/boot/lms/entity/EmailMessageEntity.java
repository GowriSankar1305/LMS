package com.boot.lms.entity;

import java.time.LocalDateTime;

import com.boot.lms.constants.AppConstants;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(schema = AppConstants.DB_SCHEMA,name = "tbl_email_message")
public class EmailMessageEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "message_id_gen")
	@SequenceGenerator(name = "message_id_gen",allocationSize = 1,initialValue = AppConstants
	.INITIAL_VALUE,schema = AppConstants.DB_SCHEMA,sequenceName = "seq_email_message")
	private Long messageId;
	private String subject;
	private String messageBody;
	private Long emailReceiverId;
	private Long emailSenderId;
	private LocalDateTime emailSentTime;
	private Boolean isEmailRead;
}
