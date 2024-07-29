package com.boot.lms.entity;

import com.boot.lms.constants.AppConstants;

import jakarta.persistence.Column;
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
@Table(name = "tbl_book_category",schema = AppConstants.DB_SCHEMA)
public class BookCategoryEntiy extends TimeStampEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cat_id_gen")
	@SequenceGenerator(name = "cat_id_gen",allocationSize = 1,initialValue = AppConstants
	.INITIAL_VALUE,sequenceName = "seq_book_category",schema = AppConstants.DB_SCHEMA)
	private Long categoryId;
	@Column(unique = true)
	private String categoryName;
}
