package com.boot.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.lms.entity.MembershipTypeEntity;

public interface MembershipTypeRepository extends JpaRepository<MembershipTypeEntity, Long>{
	MembershipTypeEntity findByMembershipTypeId(Long membershipTypeId);
	MembershipTypeEntity findByMembershipType(String membershipType);
}
