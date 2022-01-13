package com.adhd.Olivia.enums;

public enum Status {
	DIAGNOSED("Diagnosed ADHD"),
	UNDIAGNOSED("Undiagnosed ADHD"),
	SYMPTOMS("I have symptoms"),
	NOTSURE("Not Sure");
	
	private String description;
	
	Status(String description){
		this.description = description;
	}	
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public static Status getById(int id){
		switch (id) {
			case 0: return Status.DIAGNOSED;
			case 1: return Status.UNDIAGNOSED;
			case 2: return Status.SYMPTOMS;
			case 3: return Status.NOTSURE;
			default: return null;
		}			
	}
}
