package com.boot.lms.service;

import java.util.List;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.EMailMessageDto;
import com.boot.lms.dto.MemberDto;

public interface MemberService {
	public ApiResponseDto saveOrUpdateUser(MemberDto memberDto);
	public List<MemberDto> fetchMembers(Boolean memberStatus);
	public MemberDto fetchMemberById(Long memberId);
	public ApiResponseDto sendEmailToAdmin(EMailMessageDto emailMessageDto);
}
