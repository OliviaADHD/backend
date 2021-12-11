package com.adhd.Olivia.enums;

public enum Language {
	ENGLISH("English"),
	FRENCH("French"),
	SPANISH("Spanish");
	
	private String description;
	
	Language(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}
