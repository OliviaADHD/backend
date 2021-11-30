package com.adhd.Olivia.enums;

public enum SleepTime {
	WELLSLEEP(0, "I sleep well"),
	NIGHTMARES(1, "I have nightmares"),
	INSOMNIA(2, "I suffer from insomnia"),
	TOOLATETOBED(3, "I go to bed too late"),
	NARCOLEPSY(4, "Narcolepsy"),
	WAKEFREQUENTLY(5, "Waking up frequently at night");
	
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
	
	public static SleepTime getById(int id){
		SleepTime sleeps[] = SleepTime.values();
		for(SleepTime sleep: sleeps) {
			if (sleep.getId() == id) {
				return sleep;
			}
		}
		return null;		
	}
}
