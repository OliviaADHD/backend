package com.adhd.Olivia.enums;

public enum Status {
	DIAGNOSED(1, "Diagnosed ADHD"),
	UNDIAGNOSED(2, "Undiagnosed ADHD"),
	SYMPTOMS(3, "I have symptoms"),
	NOTSURE(4, "Not Sure");
	
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
}
