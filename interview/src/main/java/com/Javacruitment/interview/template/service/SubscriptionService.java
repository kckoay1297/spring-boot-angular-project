package com.Javacruitment.interview.template.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.Javacruitment.interview.template.constant.SubscriptionMessageConstant;
import com.Javacruitment.interview.template.dao.SubscriptionDao;
import com.Javacruitment.interview.template.enums.SubscriptionTypeEnum;
import com.Javacruitment.interview.template.model.SubscriptionEntity;
import com.Javacruitment.interview.template.model.dto.SubscriptionFormDto;
import com.Javacruitment.interview.template.util.DateProcessUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@PropertySource("classpath:subscription_msg_en.properties")
@ConfigurationProperties
@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SubscriptionService {

	@Autowired
	SubscriptionDao subscriptionDao;
	
	@Autowired
	private Environment env;
	
	public List<SubscriptionEntity> findAll(){
		return subscriptionDao.findAll();
	}
	
	public SubscriptionFormDto createSubscription(SubscriptionFormDto subscriptionFormDto) throws Exception {
		String validationMessage = validationMessage(subscriptionFormDto);
		if(StringUtils.isBlank(validationMessage)) {
			SubscriptionEntity subscriptionEntity = convertDtoToEntity(subscriptionFormDto);
			subscriptionEntity.setInvoiceDateRecord(generateInvoiceDateInSubscriptionDateRange(subscriptionEntity.getSubscriptionType(), subscriptionEntity.getSubscriptionStartDate()
					, subscriptionEntity.getSubscriptionEndDate()));
			subscriptionEntity = subscriptionDao.createSubscription(subscriptionEntity);
			
			subscriptionFormDto.setId(subscriptionEntity.getId());
			subscriptionFormDto.setSubscriptionTypeDisplay(getSubscriptionTypeDescription(subscriptionEntity.getSubscriptionType()));
			subscriptionFormDto.setInvoiceDateRecord(subscriptionEntity.getInvoiceDateRecord());
		}else {
			subscriptionFormDto.setRemarks(validationMessage);
		}
		
		return subscriptionFormDto;
	}
	
	private String validationMessage(SubscriptionFormDto subscriptionFormDto) throws Exception {
		Date subscriptionStartDate = subscriptionFormDto.getSubscriptionStartDate();
		Date subscriptionEndDate = subscriptionFormDto.getSubscriptionEndDate();
		Long monthDifference = DateProcessUtil.getSubscriptionDateDifference(subscriptionStartDate, subscriptionEndDate);
		if(monthDifference > 3) {
			return env.getProperty(SubscriptionMessageConstant.MSG_SUBCRIPTION_DATE_MORE_THAN_3_MONTHS);
		}
		
		if(subscriptionEndDate.before(subscriptionStartDate)) {
			return env.getProperty(SubscriptionMessageConstant.MSG_SUBCRIPTION_START_DATE_LATE_THAN_END_DATE);
		}
		
		if(BigDecimal.ZERO.compareTo(subscriptionFormDto.getAmount()) >= 0) {
			return env.getProperty(SubscriptionMessageConstant.MSG_TRANSACT_AMOUNT_LESS_OR_EQUAL_ZERO);
		}
		
		return null;
	}
	
	private String getSubscriptionTypeDescription(String subscriptionType) {
		if(String.valueOf(SubscriptionTypeEnum.DAILY.getTypeId()).equals(subscriptionType)) {
			return SubscriptionTypeEnum.DAILY.getType();
		}else if(String.valueOf(SubscriptionTypeEnum.MONTHLY.getTypeId()).equals(subscriptionType)) {
			return SubscriptionTypeEnum.MONTHLY.getType();
		}else if(String.valueOf(SubscriptionTypeEnum.WEEKLY.getTypeId()).equals(subscriptionType)) {
			return SubscriptionTypeEnum.WEEKLY.getType();
		}
		
		return null;
	}
	
	private String generateInvoiceDateInSubscriptionDateRange(String subscriptionType, Date subscriptionDateStart, Date subscriptionDateEnd) {
		String subscriptionDateStartStr = DateProcessUtil.convertDateToString(subscriptionDateStart, DateProcessUtil.CLIENT_DISPLAY_DATE_FORMAT);
		String subscriptionDateEndStr = DateProcessUtil.convertDateToString(subscriptionDateEnd, DateProcessUtil.CLIENT_DISPLAY_DATE_FORMAT);
		String finalInvoiceDateString = "";
		if(String.valueOf(SubscriptionTypeEnum.DAILY.getTypeId()).equals(subscriptionType)) {
			finalInvoiceDateString = subscriptionDateStartStr + " to " + subscriptionDateEndStr;
		}else if(String.valueOf(SubscriptionTypeEnum.MONTHLY.getTypeId()).equals(subscriptionType)) {
			Date newDate = subscriptionDateStart;
			finalInvoiceDateString += subscriptionDateStartStr ;
			while(newDate.compareTo(subscriptionDateEnd) < 0) {
				newDate = DateUtils.addMonths(newDate, 1);
				if(newDate.compareTo(subscriptionDateEnd) < 0) {
					finalInvoiceDateString += ", " + DateProcessUtil.convertDateToString(newDate, DateProcessUtil.CLIENT_DISPLAY_DATE_FORMAT);
				}
			}
		}else if(String.valueOf(SubscriptionTypeEnum.WEEKLY.getTypeId()).equals(subscriptionType)) {
			Date newDate = subscriptionDateStart;
			finalInvoiceDateString += subscriptionDateStartStr ;
			while(newDate.compareTo(subscriptionDateEnd) < 0) {
				newDate = DateUtils.addWeeks(newDate, 1);
				if(newDate.compareTo(subscriptionDateEnd) < 0) {
					finalInvoiceDateString += ", " + DateProcessUtil.convertDateToString(newDate, DateProcessUtil.CLIENT_DISPLAY_DATE_FORMAT);
				}
			}
		}else {
			System.out.println("subscriptionType=" + subscriptionType);
		}
		
		return finalInvoiceDateString;
	}
	
	private SubscriptionEntity convertDtoToEntity(SubscriptionFormDto subscriptionFormDto) {
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
		subscriptionEntity.setAmount(subscriptionFormDto.getAmount());
		subscriptionEntity.setSubscriptionStartDate(subscriptionFormDto.getSubscriptionStartDate());
		subscriptionEntity.setSubscriptionEndDate(subscriptionFormDto.getSubscriptionEndDate());
		subscriptionEntity.setSubscriptionType(subscriptionFormDto.getSubscriptionType());
		return subscriptionEntity;
	}
	
}
