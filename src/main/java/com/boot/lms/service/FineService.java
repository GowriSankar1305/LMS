package com.boot.lms.service;

import java.util.List;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.FineDto;

public interface FineService {
	public ApiResponseDto addFine(FineDto fineDto);
	public FineDto fetchFineDetails(Long fineId);
	public List<FineDto> fetchAllFinesOfAMember(Long memberId);
}
