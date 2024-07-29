package com.boot.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.lms.entity.MembershipEntity;
import com.boot.lms.enums.MembershipStatusEnum;


public interface MembershipEntityRepository extends JpaRepository<MembershipEntity, Long> {
	MembershipEntity findByMembershipId(Long membershipId);
	MembershipEntity findByMember_MemberIdAndMembershipStatus(Long memberId,MembershipStatusEnum membershipStatus);
}
