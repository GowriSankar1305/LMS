package com.boot.lms.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.lms.dto.AdminDto;
import com.boot.lms.dto.ApiResponse;
import com.boot.lms.entity.AdminEntity;
import com.boot.lms.entity.AppUserEntity;
import com.boot.lms.enums.RoleEnum;
import com.boot.lms.repository.AdminEntityRepository;
import com.boot.lms.service.AdminService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminEntityRepository adminEntityRepository;
	
	@Override
	@Transactional
	public ApiResponse saveAdmin(AdminDto adminDto) {
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
		return new ApiResponse("Admin saved successfully!", 200);
	}
}
