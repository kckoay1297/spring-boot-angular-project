package com.Javacruitment.interview.template.model.dto;

import java.io.Serializable;

import com.Javacruitment.interview.template.model.SubscriptionEntity;

public class SubscriptionFormDto extends SubscriptionEntity implements Serializable {


	private static final long serialVersionUID = -8037626976596766938L;
	
	private String remarks;
	private String subscriptionTypeDisplay;
	private String creationStatus;
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSubscriptionTypeDisplay() {
		return subscriptionTypeDisplay;
	}

	public void setSubscriptionTypeDisplay(String subscriptionTypeDisplay) {
		this.subscriptionTypeDisplay = subscriptionTypeDisplay;
	}

	public String getCreationStatus() {
		return creationStatus;
	}

	public void setCreationStatus(String creationStatus) {
		this.creationStatus = creationStatus;
	}
	
	
}
