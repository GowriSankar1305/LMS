package com.boot.lms.entity;

import java.time.LocalDate;
import java.util.List;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.enums.ReservationStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Getter
@Setter
@Entity
@Table(name = "tbl_book_reservation",schema = AppConstants.DB_SCHEMA)
public class BookReservationEntity extends TimeStampEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "res_id_gen")
	@SequenceGenerator(name = "res_id_gen",allocationSize = 1,initialValue = AppConstants
	.INITIAL_VALUE,schema = AppConstants.DB_SCHEMA,sequenceName = "seq_book_reservation")
	private Long reservationId;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name="tbl_reserved_books",schema = AppConstants.DB_SCHEMA,
			joinColumns = {@JoinColumn(name = "reservation_id")},
			inverseJoinColumns = {@JoinColumn(name = "book_id")})
	private List<BookEntity> books;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private MemberEntity member;
	private LocalDate reservationDate;
	@Enumerated(EnumType.STRING)
	private ReservationStatusEnum status;
}
