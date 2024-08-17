package com.boot.lms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.boot.lms.constants.AppConstants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_book",schema = AppConstants.DB_SCHEMA)
public class BookEntity extends TimeStampEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "book_id_gen")
	@SequenceGenerator(name = "book_id_gen",allocationSize = 1,initialValue = 
	AppConstants.INITIAL_VALUE,schema = AppConstants.DB_SCHEMA,sequenceName = "seq_book")
	private Long bookId;
	@Column(unique = true)
	private String isbn;
	private String bookTitle;
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_book_author",schema = AppConstants.DB_SCHEMA,
	joinColumns = {@JoinColumn(name ="book_id")},
	inverseJoinColumns = {@JoinColumn(name = "author_id")})
	private List<AuthorEntity> authors;
	private LocalDate publishedDate;
	private Integer noOfPages;
	private String coverImage;
	private String bookDescription;
	private Boolean isAvailable;
	private Integer noOfAvailableCopies;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private BookCategoryEntiy bookCategory;
	private BigDecimal bookPrice;
}
