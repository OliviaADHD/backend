package com.adhd.Olivia.enums;

public enum Symptoms {
	TARDINESS("Tardiness in achieving your goals"),
	DIFFICULTIESFOCUSED("Difficulty keeping your mind focused"),
	TROUBLESATTHEBEGINNING("Trouble in starting"),
	EASILTYDISTRACTED("Being easily distracted"),
	IMPULSIVE("Impulsive behavior");
	
	private String description;
	
	Symptoms(String description){
		
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public static Symptoms getById(int id){
		switch (id) {
			case 0: return Symptoms.TARDINESS;
			case 1: return Symptoms.DIFFICULTIESFOCUSED;
			case 2: return Symptoms.TROUBLESATTHEBEGINNING;
			case 3: return Symptoms.EASILTYDISTRACTED;
			case 4: return Symptoms.IMPULSIVE;
			default: return null;
		}		
	}
}
