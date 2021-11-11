package com.adhd.Olivia.enums;

public enum Symptoms {
	TARDINESS(1, "Tardiness in achieving your goals"),
	DIFFICULTIESFOCUSED(2, "Difficulty keeping your mind focused"),
	TROUBLESATTHEBEGINNING(3, "Trouble in starting"),
	EASILTYDISTRACTED(4, "Being easily distracted"),
	IMPULSIVE(5, "Impulsive behavior");
	
	private String description;
	private int id;
	
	Symptoms(int id, String description){
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
	
	public static Symptoms getById(int id){
		Symptoms symps[] = Symptoms.values();
		for(Symptoms symp: symps) {
			if (symp.getId() == id) {
				return symp;
			}
		}
		return null;		
	}
}
