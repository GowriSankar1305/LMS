package com.boot.lms.entity;

import com.boot.lms.constants.AppConstants;

import jakarta.persistence.Entity;
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
@Table(name = "tbl_address",schema = AppConstants.DB_SCHEMA)
public class AddressEntity extends TimeStampEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "addrs_id_gen")
	@SequenceGenerator(name = "addrs_id_gen",schema = AppConstants.DB_SCHEMA,
	sequenceName = "seq_address",allocationSize = 1,initialValue = AppConstants.INITIAL_VALUE)
	private Long addressId;
	private String landmark;
	private String city;
	private String state;
	private String pincode;
	private String addressLine;
}
