package com.boot.lms.service;

import java.util.List;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.MembershipDto;
import com.boot.lms.dto.MembershipTypeDto;

public interface MembershipService {
	public ApiResponseDto createMemberShipType(MembershipTypeDto mstDto);
	public List<MembershipTypeDto> fetchAllMembershipTypes();
	public ApiResponseDto addLibraryMembership(MembershipDto membershipDto);
	public MembershipDto fetchMembershipDetails(Long membershipId);
	public MembershipDto fetchActiveMembershipByMemberId(Long memberId);
	public ApiResponseDto updateMembershipStatus(MembershipDto membershipDto);
}
