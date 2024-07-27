package com.boot.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.lms.entity.MembershipEntity;


public interface MembershipEntityRepository extends JpaRepository<MembershipEntity, Long> {
	MembershipEntity findByMembershipId(Long membershipId);
	MembershipEntity findByMember_MemberId(Long memberId);
}
