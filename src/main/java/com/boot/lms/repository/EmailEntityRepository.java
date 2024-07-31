package com.boot.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.lms.entity.EmailMessageEntity;


public interface EmailEntityRepository extends JpaRepository<EmailMessageEntity, Long> {
	EmailMessageEntity findByMessageId(Long messageId);
}
