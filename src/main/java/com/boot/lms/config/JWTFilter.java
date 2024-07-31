package com.boot.lms.config;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.MembershipDto;
import com.boot.lms.dto.ToDateDto;
import com.boot.lms.enums.MembershipStatusEnum;
import com.boot.lms.service.MembershipService;
import com.boot.lms.util.ThreadLocalUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	private MembershipService membershipService;
	@Value("${lms.membership.active.routes}")
	private List<String> membershipActiveRoutes;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("==== intercepting the request to application ====");
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("principalId", 1l);
		ThreadLocalUtility.set(requestMap);
		log.info("request path----> {}",request.getContextPath());
		log.info("request uri--> {}",request.getRequestURI());
		log.info("request servelt path----> {}",request.getServletPath());
		
		//refreshMemershipStatus(null);
		
		filterChain.doFilter(request, response);
		/*
		 * response.setStatus(400);
		 * response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		 * response.setCharacterEncoding("UTF-8"); PrintWriter printWriter =
		 * response.getWriter(); printWriter.print(new
		 * ObjectMapper().writeValueAsString(new
		 * ApiResponseDto("Unable to accept the requests now!", 400)));
		 * printWriter.flush(); return;
		 */
	}
	
	private void refreshMemershipStatus(Long memberId)	{
	  MembershipDto	membershipDto = membershipService.fetchActiveMembershipByMemberId(memberId);
	  ToDateDto toDateDto = membershipDto.getToDate();
	  LocalDate toDate = LocalDate.of(toDateDto.getYear(), toDateDto.getMonth(), toDateDto.getDay());
	  if(LocalDate.now().compareTo(toDate) < 0)	{
		  log.info("---- membership expired ----");
		  membershipDto.setIsMembershipActive(false);
		  membershipDto.setMembershipStatus(MembershipStatusEnum.EXPIRED.name());
		  ApiResponseDto apiResponseDto = membershipService.updateMembershipStatus(membershipDto);
		  log.info("membership response--> {}",apiResponseDto);
		  throw new RuntimeException("Library membership expired for member!");
	  }
	}

}
