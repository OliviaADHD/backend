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
	
	public static Language getById(int id) {
		switch (id) {
			case 0: return Language.ENGLISH;
			case 1: return Language.FRENCH;
			case 2: return Language.SPANISH;
			default: return null;
		}		
	}
}
