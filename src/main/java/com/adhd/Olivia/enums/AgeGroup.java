package com.adhd.Olivia.enums;

public enum AgeGroup {
	EIGHTEEN(0, "18-25 years"),
	TWENTYFIVE(1, "25-35 years"),
	THIRTYFIVE(2, "35-45 years"),
	FOURTYFIVE(3, "45-55 yeards"),
	FIFTYFIVE(4, "55 and above");
	
	private String description;
	private int id;
	AgeGroup(int id, String description){
		this.id = id;
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public static AgeGroup getById(int id){
		AgeGroup response = null;
		AgeGroup ages[] = AgeGroup.values();
		for(AgeGroup age: ages) {
			if (age.getId() == id) {
				response = age;
				break;
			}
		}
		return response;		
	}
	
}
