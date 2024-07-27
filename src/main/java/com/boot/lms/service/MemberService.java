package com.boot.lms.service;

import java.util.List;

import com.boot.lms.dto.ApiResponse;
import com.boot.lms.dto.MemberDto;

public interface MemberService {
	public ApiResponse saveOrUpdateUser(MemberDto memberDto);
	public List<MemberDto> fetchMembers(Boolean memberStatus);
	public MemberDto fetchMemberById(Long memberId);
}
