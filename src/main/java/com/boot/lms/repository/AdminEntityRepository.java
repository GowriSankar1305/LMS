package com.boot.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.lms.entity.AdminEntity;

public interface AdminEntityRepository extends JpaRepository<AdminEntity, Long> {
	AdminEntity findByAdminId(Long adminId);
}
