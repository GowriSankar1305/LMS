package com.boot.lms.service;

import java.util.List;

import com.boot.lms.dto.ApiResponse;
import com.boot.lms.dto.MembershipDto;
import com.boot.lms.dto.MembershipTypeDto;

public interface MembershipService {
	public ApiResponse createMemberShipType(MembershipTypeDto mstDto);
	public List<MembershipTypeDto> fetchAllMembershipTypes();
	public ApiResponse addLibraryMembership(MembershipDto membershipDto);
	public MembershipDto fetchMembershipDetails(Long membershipId);
	public List<MembershipDto> fetchMembershipDetailsByMemberId(Long memberId);
}
