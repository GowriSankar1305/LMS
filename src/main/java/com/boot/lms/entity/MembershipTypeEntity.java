package com.boot.lms.entity;

import java.math.BigDecimal;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.enums.MembershipTimelineEnum;

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
@Table(name = "tbl_membership_type",schema = AppConstants.DB_SCHEMA)
public class MembershipTypeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "mem_type_id_gen")
	@SequenceGenerator(name = "mem_type_id_gen",allocationSize = 1,initialValue = AppConstants
	.INITIAL_VALUE,schema = AppConstants.DB_SCHEMA,sequenceName = "seq_membership_type")
	private Long membershipTypeId;
	@Column(unique = true)
	private String membershipType;
	private Short timelineLimit;
	private Short borrowingLimit;
	private BigDecimal cost;
	@Enumerated(EnumType.STRING)
	private MembershipTimelineEnum membershipTimeline;
}
