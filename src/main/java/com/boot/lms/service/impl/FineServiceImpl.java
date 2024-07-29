package com.boot.lms.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.boot.lms.dto.ApiResponseDto;
import com.boot.lms.dto.FineDto;
import com.boot.lms.dto.PaymentDateDto;
import com.boot.lms.entity.BookLoanEntity;
import com.boot.lms.entity.FineEntity;
import com.boot.lms.entity.MemberEntity;
import com.boot.lms.enums.FineTypeEnum;
import com.boot.lms.enums.PaymentTypeEnum;
import com.boot.lms.exception.UserInputException;
import com.boot.lms.repository.BookLoanEntityRepository;
import com.boot.lms.repository.FineEntityRepository;
import com.boot.lms.repository.MemberEntityRepository;
import com.boot.lms.service.FineService;
import com.boot.lms.util.LmsUtility;

import lombok.AllArgsConstructor;

@Service
//@AllArgsConstructor
public class FineServiceImpl implements FineService {

	@Autowired
	private FineEntityRepository fineEntityRepository;
	@Autowired
	private BookLoanEntityRepository bookLoanEntityRepository;
	@Autowired
	private MemberEntityRepository memberEntityRepository;
	
	@Override
	@Transactional
	public ApiResponseDto addFine(FineDto fineDto) {
		BookLoanEntity bookLoanEntity = bookLoanEntityRepository.findByLoanId(fineDto.getLoanId());
		MemberEntity memberEntity = memberEntityRepository.findByMemberId(fineDto.getMemberId());
		FineTypeEnum fineTypeEnum = null;
		PaymentTypeEnum paymentTypeEnum = null;
		LocalDate paymentDate = null;
		if(Objects.isNull(memberEntity))	{
			throw new UserInputException("Invalid member id!");
		}
		if(Objects.isNull(bookLoanEntity))	{
			throw new UserInputException("Invalid book loan id!");
		}
		try	{
			fineTypeEnum = FineTypeEnum.valueOf(fineDto.getFineType());
		}
		catch(Exception e)	{
			throw new UserInputException("Invalid fine type!");
		}
		try	{
			paymentTypeEnum = PaymentTypeEnum.valueOf(fineDto.getFineType());
		}
		catch(Exception e)	{
			throw new UserInputException("Invalid payment type!");
		}
		
		PaymentDateDto paymentDateDto = fineDto.getPaymentDateDto();
		paymentDate = LocalDate.of(paymentDateDto.getYear()
				, paymentDateDto.getMonth(), paymentDateDto.getDay());
		if(paymentDate.compareTo(LocalDate.now()) > 0)	{
			throw new UserInputException("Invalid payment date!");
		}
		FineEntity fineEntity = new FineEntity();
		fineEntity.setAmount(LmsUtility.parseAmount(fineDto.getAmount()));
		fineEntity.setBookLoan(bookLoanEntity);
		fineEntity.setFineType(fineTypeEnum);
		fineEntity.setMember(memberEntity);
		fineEntity.setPaymentDate(paymentDate);
		fineEntity.setPaymentType(paymentTypeEnum);
		fineEntityRepository.save(fineEntity);
		return new ApiResponseDto("Fine details added successfully!", 200);
	}

	@Override
	public FineDto fetchFineDetails(Long fineId) {
		FineEntity fineEntity = fineEntityRepository.findByFineId(fineId);
		if(Objects.isNull(fineEntity))	{
			throw new UserInputException("Invalid fine id. no record found!");
		}
		return mapToFineDto.apply(fineEntity);
	}

	@Override
	public List<FineDto> fetchAllFinesOfAMember(Long memberId) {
		List<FineDto> fineDtos = null;
		List<FineEntity> fineEntities = fineEntityRepository.findAll();
		if(!CollectionUtils.isEmpty(fineEntities))	{
			fineDtos = fineEntities.stream().map(mapToFineDto).collect(Collectors.toList());
		}
		return fineDtos;
	}

	private Function<FineEntity, FineDto> mapToFineDto = (entity) -> {
		FineDto dto = new FineDto();
		dto.setAmount(entity.getAmount().toString());
		dto.setFineId(dto.getFineId());
		dto.setPaymentType(entity.getPaymentType().name());
		PaymentDateDto paymentDateDto = new PaymentDateDto();
		paymentDateDto.setDay(entity.getPaymentDate().getDayOfMonth());
		paymentDateDto.setMonth(entity.getPaymentDate().getMonthValue());
		paymentDateDto.setYear(entity.getPaymentDate().getYear());
		dto.setPaymentDateDto(paymentDateDto);
		return dto;
	};
}
