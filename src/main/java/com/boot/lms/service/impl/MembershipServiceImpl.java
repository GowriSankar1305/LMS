package com.boot.lms.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.FromDateDto;
import com.boot.lms.dto.MembershipDto;
import com.boot.lms.dto.MembershipTypeDto;
import com.boot.lms.dto.ToDateDto;
import com.boot.lms.entity.MemberEntity;
import com.boot.lms.entity.MembershipEntity;
import com.boot.lms.entity.MembershipTypeEntity;
import com.boot.lms.enums.MembershipStatusEnum;
import com.boot.lms.enums.MembershipTimelineEnum;
import com.boot.lms.enums.PaymentTypeEnum;
import com.boot.lms.exception.UserInputException;
import com.boot.lms.repository.MemberEntityRepository;
import com.boot.lms.repository.MembershipEntityRepository;
import com.boot.lms.repository.MembershipTypeRepository;
import com.boot.lms.service.MembershipService;
import com.boot.lms.util.LmsUtility;
import com.boot.lms.util.ThreadLocalUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MembershipServiceImpl implements MembershipService {

	@Autowired
	private MembershipTypeRepository membershipTypeRepository;
	@Autowired
	private MemberEntityRepository memberEntityRepository;
	@Autowired
	private MembershipEntityRepository membershipEntityRepository;
	
	@Override
	public ApiResponseDto createMemberShipType(MembershipTypeDto mstDto) {
		log.info("mbspDto-------> {}",mstDto.toString());
		if(Objects.nonNull(membershipTypeRepository.findByMembershipType(mstDto.getMembershipType())))	{
			throw new UserInputException("Membership type already exists!");
		}
		MembershipTypeEntity membershipTypeEntity = new MembershipTypeEntity();
		membershipTypeEntity.setBorrowingLimit(mstDto.getBorrowingLimit());
		membershipTypeEntity.setCost(LmsUtility.parseAmount(mstDto.getCost()));
		membershipTypeEntity.setMembershipType(mstDto.getMembershipType().toUpperCase());
		membershipTypeEntity.setTimelineLimit(mstDto.getTimelineLimit());
		membershipTypeEntity.setMembershipTimeline(MembershipTimelineEnum.valueOf(mstDto.getMembershipTimeline()));
		log.info("mbsp timeline type---------> {}",membershipTypeEntity.getMembershipTimeline());
		membershipTypeRepository.save(membershipTypeEntity);
		return new ApiResponseDto("Membership type created successfully!", 200);
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
		dto.setTimelineLimit(entity.getTimelineLimit());
		dto.setMembershipTimeline(entity.getMembershipTimeline().name());
		dto.setMembershipTypeId(entity.getMembershipTypeId());
		return dto;
	};

	@Override
	public ApiResponseDto addLibraryMembership(MembershipDto membershipDto) {
		PaymentTypeEnum paymentTypeEnum = null;
		MemberEntity memberEntity = memberEntityRepository.findByMemberId(membershipDto.getMemberId());
		MembershipTypeEntity membershipTypeEntity = membershipTypeRepository
				.findByMembershipTypeId(membershipDto.getMemberShipTypeId());
		if(Objects.isNull(memberEntity))	{
			throw new UserInputException("Invalid member id!");
		}
		if(Objects.isNull(membershipTypeEntity))	{
			throw new UserInputException("Invalid membership type!");
		}
		try	{
			paymentTypeEnum = PaymentTypeEnum.valueOf(membershipDto.getPaymentType());
		}
		catch(Exception e)	{
			throw new UserInputException("Invalid payment type!");
		}
		
		Long principalId = (Long)ThreadLocalUtility.get().get(AppConstants.PRINCIPAL_ID);
		MembershipEntity membershipEntity = new MembershipEntity();
		setMembershipdates(membershipEntity,membershipTypeEntity);
		membershipEntity.setAmountPaid(LmsUtility.parseAmount(membershipDto.getAmountPaid()));
		membershipEntity.setMember(memberEntity);
		membershipEntity.setIsMembershipActive(Boolean.TRUE);
		membershipEntity.setMembershipType(membershipTypeEntity);
		membershipEntity.setPaymentType(paymentTypeEnum);
		membershipEntity.setCreatedBy(principalId);
		membershipEntity.setModifiedBy(principalId);
		membershipEntity.setCreatedTime(LocalDateTime.now());
		membershipEntity.setModifiedTime(LocalDateTime.now());
		membershipEntity.setMembershipStatus(MembershipStatusEnum.ACTIVE);
		membershipEntityRepository.save(membershipEntity);
		return new ApiResponseDto("Membership added successfully!", 200);
	}

	private void setMembershipdates(MembershipEntity membershipEntity,MembershipTypeEntity membershipTypeEntity)	{
		MembershipTimelineEnum membershipTimeline = membershipTypeEntity.getMembershipTimeline();
		Short timeLineLimit = membershipTypeEntity.getTimelineLimit();
		LocalDate fromDate = LocalDate.now();
		LocalDate toDate = LocalDate.now();
		if(membershipTimeline.equals(MembershipTimelineEnum.MONTHS))	{
			for(short months = 0 ; months < timeLineLimit ; months++)	{
					toDate = toDate.plusDays(30l);
			}
		}
		else if(membershipTimeline.equals(MembershipTimelineEnum.WEEKS))	{
			for(short weeks = 0 ; weeks < timeLineLimit ; weeks++)	{
				toDate = toDate.plusDays(7l);
			}
		}
		else if(membershipTimeline.equals(MembershipTimelineEnum.YEARS))	{
			for(short years = 0 ; years < timeLineLimit ; years++)	{
				toDate = toDate.plusDays(365l);
			}
		}
		membershipEntity.setFromDate(fromDate);
		membershipEntity.setToDate(toDate);
	}
	
	private Function<MembershipEntity, MembershipDto> mapToMspDto = (entity) -> {
		MembershipDto membershipDto = new MembershipDto();
		membershipDto.setMembershipId(entity.getMembershipId());
		membershipDto.setAmountPaid(entity.getAmountPaid().toString());
		FromDateDto fromDateDto = new FromDateDto();
		fromDateDto.setDay(entity.getFromDate().getDayOfMonth());
		fromDateDto.setMonth(entity.getFromDate().getMonthValue());
		fromDateDto.setYear(entity.getFromDate().getYear());
		ToDateDto toDateDto = new ToDateDto();
		toDateDto.setDay(entity.getToDate().getDayOfMonth());
		toDateDto.setMonth(entity.getToDate().getMonthValue());
		toDateDto.setYear(entity.getToDate().getYear());
		membershipDto.setFromDate(fromDateDto);
		membershipDto.setToDate(toDateDto);
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
	public MembershipDto fetchActiveMembershipByMemberId(Long memberId) {
		MembershipEntity membershipEntity = membershipEntityRepository
				.findByMember_MemberIdAndMembershipStatus(memberId,MembershipStatusEnum.ACTIVE);
		if(Objects.isNull(membershipEntity))	{
			throw new UserInputException("Invalid member id!");
		}
		return mapToMspDto.apply(membershipEntity);
	}

	@Override
	public ApiResponseDto updateMembershipStatus(MembershipDto membershipDto) {
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		int result = membershipEntityRepository.updateMembershipStatus(membershipDto.getMembershipId(),
				membershipDto.getIsMembershipActive(), MembershipStatusEnum.valueOf(membershipDto.getMembershipStatus()));
		log.info("no of rows effected by the query----> {}",result);
		if(result == 1)	{
			apiResponseDto.setMessage("Membership status updated successfully!");
			apiResponseDto.setStatus(200);
		}
		else	{
			apiResponseDto.setMessage("Not able to modify Membership status!");
			apiResponseDto.setStatus(500);
		}
		return apiResponseDto;
	}
}
