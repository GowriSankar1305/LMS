package com.boot.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.lms.entity.AppUserEntity;

public interface AppUserEntityRepository extends JpaRepository<AppUserEntity, Long> {
	AppUserEntity findByUserName(String userName);
}
