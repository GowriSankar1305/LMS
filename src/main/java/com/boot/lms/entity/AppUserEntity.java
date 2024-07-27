package com.boot.lms.entity;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.enums.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_app_user",schema = AppConstants.DB_SCHEMA)
public class AppUserEntity extends TimeStampEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "appusr_id_ gen")
	@SequenceGenerator(name = "appusr_id_ gen",allocationSize = 1,initialValue = 
	AppConstants.INITIAL_VALUE,schema = AppConstants.DB_SCHEMA,sequenceName = "seq_app_user")
	private Long appUserId;
	@Column(unique = true)
	private String userName;
	private String password;
	private Boolean isActive;
	@Enumerated(EnumType.STRING)
	private RoleEnum userRole;
}
