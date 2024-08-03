package com.boot.lms.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.MembershipDto;
import com.boot.lms.dto.ToDateDto;
import com.boot.lms.enums.MembershipStatusEnum;
import com.boot.lms.exception.MembershipExpiredException;
import com.boot.lms.exception.UserInputException;
import com.boot.lms.service.MembershipService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LmsUtility {

	@Autowired
	private MembershipService membershipService;

	public static BigDecimal parseAmount(String amount) {
		try {
			return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
		} catch (Exception e) {
			throw new UserInputException("Invalid amount field!");
		}
	}
	
	public void refreshMemershipStatus(Long memberId)	{
		  MembershipDto	membershipDto = membershipService.fetchActiveMembershipByMemberId(memberId);
		  ToDateDto toDateDto = membershipDto.getToDate();
		  LocalDate toDate = LocalDate.of(toDateDto.getYear(), toDateDto.getMonth(), toDateDto.getDay());
		  if(LocalDate.now().compareTo(toDate) < 0)	{
			  log.info("---- membership expired ----");
			  membershipDto.setIsMembershipActive(false);
			  membershipDto.setMembershipStatus(MembershipStatusEnum.EXPIRED.name());
			  ApiResponseDto apiResponseDto = membershipService.updateMembershipStatus(membershipDto);
			  log.info("membership response--> {}",apiResponseDto);
			  throw new MembershipExpiredException("Library membership expired for member " + memberId);
		  }
		}
}
