package com.boot.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.lms.entity.MemberEntity;

public interface MemberEntityRepository extends JpaRepository<MemberEntity, Long> {
	MemberEntity findByMemberId(Long memberId);
	List<MemberEntity> findByAppUser_isActive(Boolean isActive);
}
