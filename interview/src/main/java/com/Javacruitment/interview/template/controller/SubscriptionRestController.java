package com.Javacruitment.interview.template.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Javacruitment.interview.template.model.SubscriptionEntity;
import com.Javacruitment.interview.template.model.dto.SubscriptionFormDto;
import com.Javacruitment.interview.template.service.SubscriptionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(SubscriptionRestController.BASE_URL)
@AllArgsConstructor
public class SubscriptionRestController {

	protected static final String BASE_URL = "/subscription";
	private static final String CREATION_URL = "/create";
	private static final String FIND_ALL_URL = "/find-all";
	private static final String SUCCESS_MSG = "Success";
	private static final String FAIL_MSG = "Failed";
	private static final String INTERNAL_ERROR_MSG = "INTERNAL SERVER ERROR";
	
	@Autowired
	SubscriptionService subscriptionService;
	
	private Logger logger = LoggerFactory.getLogger(SubscriptionRestController.class);
	
	@CrossOrigin
	@PostMapping(CREATION_URL)
	public ResponseEntity<SubscriptionFormDto> createSubscription(@RequestBody SubscriptionFormDto subscriptionDto){
		logger.info(CREATION_URL);
		try {
			subscriptionDto = subscriptionService.createSubscription(subscriptionDto);
			if(StringUtils.isNotEmpty(subscriptionDto.getRemarks())) {
				subscriptionDto.setCreationStatus(FAIL_MSG);
				return new ResponseEntity<>(subscriptionDto, HttpStatus.OK);
			}else {
				subscriptionDto.setCreationStatus(SUCCESS_MSG);
				return new ResponseEntity<>(subscriptionDto, HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
			subscriptionDto.setCreationStatus(FAIL_MSG);
			subscriptionDto.setRemarks(INTERNAL_ERROR_MSG);
			return new ResponseEntity<>(subscriptionDto, HttpStatus.OK);
		}
	}
	
	@CrossOrigin
	@GetMapping(FIND_ALL_URL)
	public ResponseEntity<List<SubscriptionEntity>> findAllResult(){
		logger.info(FIND_ALL_URL);
		try {
			List<SubscriptionEntity> subscriptionAllList = subscriptionService.findAll();
			return new ResponseEntity<>(subscriptionAllList, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
