package com.boot.lms.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.dto.AddressDto;
import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.EMailMessageDto;
import com.boot.lms.dto.MemberDto;
import com.boot.lms.entity.AddressEntity;
import com.boot.lms.entity.AppUserEntity;
import com.boot.lms.entity.MemberEntity;
import com.boot.lms.enums.RoleEnum;
import com.boot.lms.exception.UserInputException;
import com.boot.lms.repository.AdminEntityRepository;
import com.boot.lms.repository.MemberEntityRepository;
import com.boot.lms.service.EmailService;
import com.boot.lms.service.MemberService;
import com.boot.lms.util.ThreadLocalUtility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberEntityRepository memberEntityRepository;
	@Autowired
	private AdminEntityRepository adminEntityRepository;
	@Autowired
	private EmailService emailService;
	
	@Override
	public ApiResponseDto saveOrUpdateUser(MemberDto memberDto) {
		return Objects.nonNull(memberDto.getMemberId()) ? updateMember(memberDto) : saveMember(memberDto);
	}
	
	private ApiResponseDto saveMember(MemberDto memberDto)	{
		Long principalId = (Long)ThreadLocalUtility.get().get(AppConstants.PRINCIPAL_ID);
		MemberEntity memberEntity = new MemberEntity();
		AppUserEntity appUserEntity = new AppUserEntity();
		if(Objects.nonNull(memberDto.getAddress()))	{
			AddressDto addressDto = memberDto.getAddress();
			AddressEntity addressEntity = new AddressEntity();
			addressEntity.setAddressLine(addressDto.getAddressLine());
			addressEntity.setCity(addressDto.getCity());
			addressEntity.setCreatedBy(principalId);
			addressEntity.setModifiedBy(principalId);
			addressEntity.setCreatedTime(LocalDateTime.now());
			addressEntity.setModifiedTime(LocalDateTime.now());
			addressEntity.setPincode(addressDto.getPincode());
			addressEntity.setState(addressDto.getState());
			memberEntity.setAddress(addressEntity);	
		}
		appUserEntity.setCreatedBy(principalId);
		appUserEntity.setModifiedBy(principalId);
		appUserEntity.setCreatedTime(LocalDateTime.now());
		appUserEntity.setModifiedTime(LocalDateTime.now());
		appUserEntity.setUserName(memberDto.getUserName());
		appUserEntity.setPassword(memberDto.getPassword());
		appUserEntity.setIsActive(Boolean.TRUE);
		appUserEntity.setUserRole(RoleEnum.MEMBER);
		memberEntity.setAppUser(appUserEntity);
		memberEntity.setCreatedBy(principalId);
		memberEntity.setModifiedBy(principalId);
		memberEntity.setCreatedTime(LocalDateTime.now());
		memberEntity.setModifiedTime(LocalDateTime.now());
		memberEntity.setEmailId(memberDto.getEmailId());
		memberEntity.setFirstName(memberDto.getFirstName());
		memberEntity.setLastName(memberDto.getLastName());
		memberEntity.setMobileNo(memberDto.getMobileNo());
		memberEntityRepository.save(memberEntity);
		return new ApiResponseDto("Member added successfully!", 200);
	}
	
	private ApiResponseDto updateMember(MemberDto memberDto)	{
		Long principalId = (Long)ThreadLocalUtility.get().get(AppConstants.PRINCIPAL_ID);
		MemberEntity memberEntity = memberEntityRepository.findByMemberId(memberDto.getMemberId());
		if(Objects.isNull(memberEntity))	{
			throw new UserInputException("Unable to update the member. member not found!");
		}
		memberEntity.setFirstName(memberDto.getFirstName());
		memberEntity.setLastName(memberDto.getLastName());
		memberEntity.setModifiedBy(principalId);
		memberEntity.setModifiedTime(LocalDateTime.now());
		AppUserEntity appUserEntity = memberEntity.getAppUser();
		appUserEntity.setModifiedBy(principalId);
		appUserEntity.setModifiedTime(LocalDateTime.now());
		appUserEntity.setIsActive(memberDto.getIsActive());
		memberEntity.setAppUser(appUserEntity);
		if(Objects.nonNull(memberDto.getAddress()))	{
			AddressDto addressDto = memberDto.getAddress();
			AddressEntity addressEntity = memberEntity.getAddress();
			addressEntity.setAddressLine(addressDto.getAddressLine());
			addressEntity.setCity(addressDto.getCity());
			addressEntity.setLandmark(addressDto.getLandmark());
			addressEntity.setPincode(addressDto.getPincode());
			addressEntity.setState(addressDto.getState());
			addressEntity.setModifiedBy(principalId);
			addressEntity.setModifiedTime(LocalDateTime.now());
			memberEntity.setAddress(addressEntity);
		}
		memberEntityRepository.save(memberEntity);
		return new ApiResponseDto("Member updated sucessfully!", 200);
	}

	@Override
	public List<MemberDto> fetchMembers(Boolean memberStatus) {
		List<MemberDto> membersDtoList = null;
		List<MemberEntity> memberEntityList = null;
		if(Objects.isNull(memberStatus))	{
			memberEntityList = memberEntityRepository.findAll();
		}else	{
			memberEntityList = memberEntityRepository.findByAppUser_isActive(memberStatus);
		}
		if(!CollectionUtils.isEmpty(memberEntityList))	{
			membersDtoList = memberEntityList.stream().map(mapToMemberDto).collect(Collectors.toList());
		}
		return membersDtoList;
	}
	
	private Function<MemberEntity, MemberDto> mapToMemberDto = (entity) -> {
		MemberDto dto = new MemberDto();
		dto.setEmailId(entity.getEmailId());
		dto.setFirstName(entity.getFirstName());
		dto.setEmailId(entity.getEmailId());
		dto.setMemberId(entity.getMemberId());
		dto.setMobileNo(entity.getMobileNo());
		dto.setIsActive(entity.getAppUser().getIsActive());
		dto.setAddress(null);
		return dto;
	};

	private Function<AddressEntity, AddressDto> mapToAddressDto = (entity) -> {
		AddressDto dto = new AddressDto();
		dto.setAddressId(entity.getAddressId());
		dto.setAddressLine(entity.getAddressLine());
		dto.setCity(entity.getCity());
		dto.setLandmark(entity.getLandmark());
		dto.setPincode(entity.getPincode());
		dto.setState(entity.getState());
		return dto;
	};
	
	@Override
	public MemberDto fetchMemberById(Long memberId) {
		MemberEntity memberEntity = memberEntityRepository.findByMemberId(memberId);
		if(Objects.isNull(memberEntity))	{
			throw new UserInputException("Member not found with id: " + memberId);
		}
		MemberDto memberDto = mapToMemberDto.apply(memberEntity);
		memberDto.setAddress(mapToAddressDto.apply(memberEntity.getAddress()));
		return memberDto;
	}

	@Override
	public ApiResponseDto sendEmailToAdmin(EMailMessageDto emailMessageDto) {
		if(Objects.isNull(adminEntityRepository.findByAdminId(emailMessageDto.getEmailReceiverId())))	{
			throw new UserInputException("Cannot send email. Invalid receiver id!");
		}
		Long senderId = (Long) ThreadLocalUtility.get().get(AppConstants.PRINCIPAL_ID);
		log.info("Current logged in member---> {}",senderId);
		emailMessageDto.setEmailSenderId(senderId);
		return emailService.sendEmail(emailMessageDto);
	}
}
