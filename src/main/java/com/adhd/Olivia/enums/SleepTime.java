package com.adhd.Olivia.enums;

public enum SleepTime {
	WELLSLEEP(1, "I sleep well"),
	NIGHTMARES(2, "I have nightmares"),
	INSOMNIA(3, "I suffer from insomnia"),
	TOOLATETOBED(4, "I go to bed too late"),
	NARCOLEPSY(5, "Narcolepsy"),
	WAKEFREQUENTLY(6, "Waking up frequently at night");
	
	private String description;
	private int id;
	
	SleepTime(int id, String description){
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
