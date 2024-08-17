package com.boot.lms.entity;

import java.util.List;

import com.boot.lms.constants.AppConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_author",schema = AppConstants.DB_SCHEMA)
public class AuthorEntity extends TimeStampEntity {
 
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "author_id_gen")
	@SequenceGenerator(name = "author_id_gen",sequenceName = "seq_author",allocationSize = 1
	,initialValue = AppConstants.INITIAL_VALUE,schema = AppConstants.DB_SCHEMA)
	private Long authorId;
	private String firstName;
	private String lastName;
	private String authorImage;
	@Column(length = 1000)
	private String authorBio;
	@ManyToMany(mappedBy = "authors")
	private List<BookEntity> books;
}
