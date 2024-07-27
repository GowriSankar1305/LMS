package com.boot.lms.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.dto.ApiResponse;
import com.boot.lms.dto.MembershipDto;
import com.boot.lms.dto.MembershipTypeDto;
import com.boot.lms.entity.MembershipEntity;
import com.boot.lms.entity.MembershipTypeEntity;
import com.boot.lms.enums.PaymentTypeEnum;
import com.boot.lms.exception.UserInputException;
import com.boot.lms.repository.MemberEntityRepository;
import com.boot.lms.repository.MembershipEntityRepository;
import com.boot.lms.repository.MembershipTypeRepository;
import com.boot.lms.service.MembershipService;
import com.boot.lms.util.LmsUtility;
import com.boot.lms.util.ThreadLocalUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MembershipServiceImpl implements MembershipService {

	private final MembershipTypeRepository membershipTypeRepository;
	private final MemberEntityRepository memberEntityRepository;
	private final MembershipEntityRepository membershipEntityRepository;
	
	@Override
	public ApiResponse createMemberShipType(MembershipTypeDto mstDto) {
		MembershipTypeEntity membershipTypeEntity = new MembershipTypeEntity();
		membershipTypeEntity.setBorrowingLimit(mstDto.getBorrowingLimit());
		membershipTypeEntity.setCost(LmsUtility.parseAmount(mstDto.getCost()));
		membershipTypeEntity.setMembershipType(mstDto.getMembershipType().toUpperCase());
		membershipTypeEntity.setTimelineDays(mstDto.getTimelineDays());
		membershipTypeRepository.save(membershipTypeEntity);
		return new ApiResponse("Membership type created successfully!", 200);
	}

	@Override
	public List<MembershipTypeDto> fetchAllMembershipTypes() {
		List<MembershipTypeEntity> entities = membershipTypeRepository.findAll();
		List<MembershipTypeDto> dtoList = null;
		if(!CollectionUtils.isEmpty(entities))	{
			dtoList = entities.stream().map(mapToMspTypeDto).collect(Collectors.toList());
		}
		return dtoList;
	}
	
	private Function<MembershipTypeEntity, MembershipTypeDto> mapToMspTypeDto = (entity) -> {
		MembershipTypeDto dto = new MembershipTypeDto();
		dto.setBorrowingLimit(entity.getBorrowingLimit());
		dto.setCost(entity.getCost().toString());
		dto.setMembershipType(entity.getMembershipType());
		dto.setTimelineDays(entity.getTimelineDays());
		return dto;
	};

	@Override
	public ApiResponse addLibraryMembership(MembershipDto membershipDto) {
		Long principalId = (Long)ThreadLocalUtility.get().get(AppConstants.PRINCIPAL_ID);
		MembershipEntity membershipEntity = new MembershipEntity();
		membershipEntity.setAmountPaid(LmsUtility.parseAmount(membershipDto.getAmountPaid()));
		membershipEntity.setMember(memberEntityRepository
				.findByMemberId(membershipDto.getMemberId()));
		membershipEntity.setIsMembershipActive(Boolean.TRUE);
		membershipEntity.setMembershipType(membershipTypeRepository
				.findByMembershipTypeId(membershipDto.getMemberShipTypeId()));
		membershipEntity.setPaymentType(PaymentTypeEnum.valueOf(membershipDto.getPaymentType()));
		membershipEntity.setFromDate(membershipDto.getFromDate());
		membershipEntity.setToDate(membershipDto.getToDate());
		membershipEntity.setCreatedBy(principalId);
		membershipEntity.setModifiedBy(principalId);
		membershipEntity.setCreatedTime(LocalDateTime.now());
		membershipEntity.setModifiedTime(LocalDateTime.now());
		membershipEntityRepository.save(membershipEntity);
		return new ApiResponse("Membership added successfully!", 200);
	}

	private Function<MembershipEntity, MembershipDto> mapToMspDto = (entity) -> {
		MembershipDto membershipDto = new MembershipDto();
		membershipDto.setAmountPaid(entity.getAmountPaid().toString());
		membershipDto.setFromDate(entity.getFromDate());
		membershipDto.setToDate(entity.getToDate());
		membershipDto.setIsMembershipActive(entity.getIsMembershipActive());
		membershipDto.setMemberId(entity.getMember().getMemberId());
		membershipDto.setMemberShipType(entity.getMembershipType().getMembershipType());
		return membershipDto;
	};
	
	@Override
	public MembershipDto fetchMembershipDetails(Long membershipId) {
		MembershipEntity membershipEntity = membershipEntityRepository.findByMembershipId(membershipId);
		if(Objects.isNull(membershipEntity))	{
			throw new UserInputException("Invalid membership id!");
		}
		return mapToMspDto.apply(membershipEntity);
	}

	@Override
	public List<MembershipDto> fetchMembershipDetailsByMemberId(Long memberId) {
		List<MembershipDto> membershipDtos = null;
		List<MembershipEntity> membershipEntities = membershipEntityRepository.findAll();
		if(!CollectionUtils.isEmpty(membershipEntities))	{
			membershipDtos = membershipEntities.stream().map(mapToMspDto).collect(Collectors.toList());
		}
		return membershipDtos;
	}
}
