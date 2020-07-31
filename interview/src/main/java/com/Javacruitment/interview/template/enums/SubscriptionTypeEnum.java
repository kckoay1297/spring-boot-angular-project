package com.Javacruitment.interview.template.enums;

public enum SubscriptionTypeEnum {

	DAILY("daily", 1),
	WEEKLY("weekly", 2),
	MONTHLY("monthly", 3);

	private String type;
	private int typeId;
	
	SubscriptionTypeEnum(String type, int typeId) {
		this.type = type;
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
}
