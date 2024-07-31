package com.boot.lms.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.lms.constants.AppConstants;
import com.boot.lms.dto.AdminDto;
import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.EMailMessageDto;
import com.boot.lms.entity.AdminEntity;
import com.boot.lms.entity.AppUserEntity;
import com.boot.lms.enums.RoleEnum;
import com.boot.lms.exception.UserInputException;
import com.boot.lms.repository.AdminEntityRepository;
import com.boot.lms.repository.MemberEntityRepository;
import com.boot.lms.service.AdminService;
import com.boot.lms.service.EmailService;
import com.boot.lms.util.ThreadLocalUtility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminEntityRepository adminEntityRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private MemberEntityRepository memberEntityRepository;
	
	@Override
	@Transactional
	public ApiResponseDto saveAdmin(AdminDto adminDto) {
		AdminEntity adminEntity = new AdminEntity();
		adminEntity.setCreatedBy(1l);
		adminEntity.setCreatedTime(LocalDateTime.now());
		adminEntity.setModifiedBy(1l);
		adminEntity.setModifiedTime(LocalDateTime.now());
		adminEntity.setEmailId(adminDto.getEmailId());
		adminEntity.setFirstName(adminDto.getFirstName());
		adminEntity.setLastname(adminDto.getLastname());
		adminEntity.setMobileNo(adminDto.getMobileNo());
		AppUserEntity appUserEntity = new AppUserEntity();
		appUserEntity.setUserName(adminDto.getUserName());
		appUserEntity.setPassword(adminDto.getPassword());
		appUserEntity.setUserRole(RoleEnum.ADMIN);
		appUserEntity.setIsActive(Boolean.TRUE);
		appUserEntity.setCreatedBy(1l);
		appUserEntity.setModifiedBy(1l);
		appUserEntity.setCreatedTime(LocalDateTime.now());
		appUserEntity.setModifiedTime(LocalDateTime.now());
		adminEntity.setAppUser(appUserEntity);
		adminEntityRepository.save(adminEntity);
		return new ApiResponseDto("Admin saved successfully!", 200);
	}

	@Override
	public ApiResponseDto sendEmailToMember(EMailMessageDto emailMessageDto) {
		if(Objects.isNull(memberEntityRepository.findByMemberId(emailMessageDto.getEmailReceiverId())))	{
			throw new UserInputException("Cannot send email. Invalid receiver!");
		}
		Long senderId = (Long) ThreadLocalUtility.get().get(AppConstants.PRINCIPAL_ID);
		log.info("Current logged in admin---> {}",senderId);
		emailMessageDto.setEmailSenderId(senderId);
		return emailService.sendEmail(emailMessageDto);
	}
}
