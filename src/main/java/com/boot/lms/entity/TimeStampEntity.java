package com.boot.lms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class TimeStampEntity {
	private Long createdBy;
	private LocalDateTime createdTime;
	private Long modifiedBy;
	private LocalDateTime modifiedTime;
}
