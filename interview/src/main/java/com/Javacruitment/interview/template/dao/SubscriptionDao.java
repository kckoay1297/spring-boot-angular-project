package com.Javacruitment.interview.template.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Javacruitment.interview.template.model.SubscriptionEntity;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class SubscriptionDao {

	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	public List<SubscriptionEntity> findAll(){
		return subscriptionRepository.findAll();
	}
	
	public SubscriptionEntity createSubscription(SubscriptionEntity subscriptionEntity) {
		return subscriptionRepository.save(subscriptionEntity);
	}
}
