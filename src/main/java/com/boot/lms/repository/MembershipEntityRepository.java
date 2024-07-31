package com.boot.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.boot.lms.entity.MembershipEntity;
import com.boot.lms.enums.MembershipStatusEnum;


public interface MembershipEntityRepository extends JpaRepository<MembershipEntity, Long> {
	MembershipEntity findByMembershipId(Long membershipId);
	MembershipEntity findByMember_MemberIdAndMembershipStatus(Long memberId,MembershipStatusEnum membershipStatus);
	@Modifying
	@Query("update MembershipEntity set isMembershipActive = :isActive "
			+ ", membershipStatus = :status where membershipId = :id")
	Integer updateMembershipStatus(Long id,Boolean isActive,MembershipStatusEnum status);
}
