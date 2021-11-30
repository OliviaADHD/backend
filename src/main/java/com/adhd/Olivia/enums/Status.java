package com.adhd.Olivia.enums;

public enum Status {
	DIAGNOSED(0, "Diagnosed ADHD"),
	UNDIAGNOSED(1, "Undiagnosed ADHD"),
	SYMPTOMS(2, "I have symptoms"),
	NOTSURE(3, "Not Sure");
	
	private String description;
	private int id;
	
	Status(int id, String description){
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
	
	public static Status getById(int id){
		Status response = null;
		Status statuses[] = Status.values();
		for(Status stat: statuses) {
			if (stat.getId() == id) {
				response = stat;
				break;
			}
		}
		return response;		
	}
}
