package com.adhd.Olivia.enums;

public enum SleepTime {
	WELLSLEEP("I sleep well"),
	NIGHTMARES("I have nightmares"),
	INSOMNIA("I suffer from insomnia"),
	TOOLATETOBED("I go to bed too late"),
	NARCOLEPSY("Narcolepsy"),
	WAKEFREQUENTLY("Waking up frequently at night");
	
	private String description;
	
	SleepTime(String description){
		this.description = description;
	}	
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public static SleepTime getById(int id){
		switch (id) {
			case 0: return SleepTime.WELLSLEEP;
			case 1: return SleepTime.NIGHTMARES;
			case 2: return SleepTime.INSOMNIA;
			case 3: return SleepTime.TOOLATETOBED;
			case 4: return SleepTime.NARCOLEPSY;
			case 5: return SleepTime.WAKEFREQUENTLY;
			default: return null;
		}
	}
}
