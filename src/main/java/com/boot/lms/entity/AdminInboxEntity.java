package com.boot.lms.entity;

import java.util.List;

import com.boot.lms.constants.AppConstants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_admin_inbox",schema = AppConstants.DB_SCHEMA)
public class AdminInboxEntity {

	@Id
	@GeneratedValue(generator = "admin_inbox_id_gen",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "admin_inbox_id_gen",allocationSize = 1,initialValue = 
	AppConstants.INITIAL_VALUE,schema = AppConstants.DB_SCHEMA,sequenceName = "seq_admin_inbox")
	private Long adminId;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id")
	private AdminEntity adminEntity;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_admin_email_messages",schema = AppConstants.DB_SCHEMA,
	joinColumns = {@JoinColumn(name = "message_id")},
	inverseJoinColumns =  {@JoinColumn(name = "inbox_id")})
	private List<EmailMessageEntity> emailMessages;
}
 