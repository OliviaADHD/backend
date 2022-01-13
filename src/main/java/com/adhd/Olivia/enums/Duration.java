package com.adhd.Olivia.enums;

public enum Duration {
	ONEYEAR("<1 years"),
	ONETOTHREEYEARS("1-3 years"),
	THREETOSIXYEARS("3-6 years"),
	SIXORMOREYEARS("6 + years");
	
	private String description;

	Duration(String description){
		this.description = description;
	}	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public static Duration getById(int id){
		switch (id) {
			case 0: return Duration.ONEYEAR;
			case 1: return Duration.ONETOTHREEYEARS;
			case 2: return Duration.THREETOSIXYEARS;
			case 3: return Duration.SIXORMOREYEARS;
			default: return null;
		}		
	}
}
