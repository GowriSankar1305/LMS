package com.boot.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.lms.entity.FineEntity;

public interface FineEntityRepository extends JpaRepository<FineEntity, Long> {
	FineEntity findByFineId(Long fineId);
}
