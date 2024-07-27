package com.boot.lms.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.boot.lms.dto.ApiResponse;
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
@AllArgsConstructor
public class FineServiceImpl implements FineService {

	private final FineEntityRepository fineEntityRepository;
	private final BookLoanEntityRepository bookLoanEntityRepository;
	private final MemberEntityRepository memberEntityRepository;
	
	@Override
	@Transactional
	public ApiResponse addFine(FineDto fineDto) {
		BookLoanEntity bookLoanEntity = bookLoanEntityRepository.findByLoanId(fineDto.getLoanId());
		MemberEntity memberEntity = memberEntityRepository.findByMemberId(fineDto.getMemberId());
		if(Objects.isNull(memberEntity))	{
			throw new UserInputException("Invalid member id!");
		}
		if(Objects.isNull(bookLoanEntity))	{
			throw new UserInputException("Invalid book loan id!");
		}
		FineEntity fineEntity = new FineEntity();
		fineEntity.setAmount(LmsUtility.parseAmount(fineDto.getAmount()));
		fineEntity.setBookLoan(bookLoanEntity);
		fineEntity.setFineType(FineTypeEnum.valueOf(fineDto.getFineType()));
		fineEntity.setMember(memberEntity);
		PaymentDateDto paymentDateDto = fineDto.getPaymentDateDto();
		fineEntity.setPaymentDate(LocalDate.of(paymentDateDto.getYear()
				, paymentDateDto.getMonth(), paymentDateDto.getDay()));
		fineEntity.setPaymentType(PaymentTypeEnum.valueOf(fineDto.getFineType()));
		fineEntityRepository.save(fineEntity);
		return new ApiResponse("Fine details added successfully!", 200);
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
