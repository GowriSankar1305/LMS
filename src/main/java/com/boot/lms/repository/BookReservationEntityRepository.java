package com.boot.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.lms.entity.BookReservationEntity;


public interface BookReservationEntityRepository extends JpaRepository<BookReservationEntity, Long> {
	BookReservationEntity findByReservationId(Long reservationId);
	List<BookReservationEntity> findByMember_MemberId(Long memberId);
}
