package com.boot.lms.entity;

import com.boot.lms.constants.AppConstants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_member",schema = AppConstants.DB_SCHEMA)
public class MemberEntity extends TimeStampEntity {
	
	@Id
	@GeneratedValue(generator = "member_id_gen",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "member_id_gen",allocationSize = 1,initialValue = AppConstants
	.INITIAL_VALUE,schema = AppConstants.DB_SCHEMA,sequenceName = "seq_member")
	private Long memberId;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String mobileNo;
	@Column(unique = true)
	private String emailId;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	private AppUserEntity appUser;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private AddressEntity address;
}
