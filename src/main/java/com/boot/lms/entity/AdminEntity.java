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
@Table(name = "tbl_admin",schema = AppConstants.DB_SCHEMA)
public class AdminEntity extends TimeStampEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "admin_id_gen")
	@SequenceGenerator(sequenceName = "seq_admin",allocationSize = 1,initialValue = 
	AppConstants.INITIAL_VALUE,name = "admin_id_gen",schema = AppConstants.DB_SCHEMA)
	private Long adminId;
	private String firstName;
	private String lastname;
	@Column(unique = true)
	private String mobileNo;
	@Column(unique = true)
	private String emailId;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	private AppUserEntity appUser;
}
