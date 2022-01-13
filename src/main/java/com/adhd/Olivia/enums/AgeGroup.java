package com.adhd.Olivia.enums;

public enum AgeGroup {
	EIGHTEEN("18-25 years"),
	TWENTYFIVE("25-35 years"),
	THIRTYFIVE("35-45 years"),
	FOURTYFIVE("45-55 yeards"),
	FIFTYFIVE("55 and above");
	
	private String description;

	AgeGroup (String description){
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public static AgeGroup getById(int id) {
		switch (id) {
			case 0: return AgeGroup.EIGHTEEN;
			case 1: return AgeGroup.TWENTYFIVE;
			case 2: return AgeGroup.THIRTYFIVE;
			case 3: return AgeGroup.FOURTYFIVE;
			case 4: return AgeGroup.FIFTYFIVE;
			default: return null;
		}		
	}
	
}
