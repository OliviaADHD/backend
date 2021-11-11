package com.adhd.Olivia.enums;

public enum Duration {
	ONEYEAR(1, "<1 years"),
	ONETOTHREEYEARS(2, "1-3 years"),
	THREETOSIXYEARS(3, "3-6 years"),
	SIXORMOREYEARS(4, "6 + years");
	
	private String description;
	private int id;
	Duration(int id, String description){
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
	
	public static Duration getById(int id){
		Duration response = null;
		Duration durs[] = Duration.values();
		for(Duration dur: durs) {
			if (dur.getId() == id) {
				response = dur;
				break;
				
			}
		}
		return response;		
	}
}
