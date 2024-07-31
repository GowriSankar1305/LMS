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
@Table(name = "tbl_member_inbox",schema = AppConstants.DB_SCHEMA)
public class MemberInboxEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "member_inbox_id_gen")
	@SequenceGenerator(name = "member_inbox_id_gen",sequenceName = "seq_member_inbox",
	allocationSize = 1,initialValue = AppConstants.INITIAL_VALUE,schema = AppConstants.DB_SCHEMA)
	private Long inboxId;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private MemberEntity memberEntity;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_member_email_messages",schema = AppConstants.DB_SCHEMA,
		joinColumns = {@JoinColumn(name = "message_id")},
		inverseJoinColumns =  {@JoinColumn(name = "inbox_id")})
	private List<EmailMessageEntity> emailMessages;
}
