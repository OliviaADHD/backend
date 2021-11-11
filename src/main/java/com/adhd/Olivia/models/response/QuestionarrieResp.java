package com.adhd.Olivia.models.response;

import java.util.List;

public class QuestionarrieResp {

	private int ageGroup;
	
	private int status;
	
	private int duration;
	
	private List<Integer> symptoms;
	
	private List<Integer> sleepTime;

	public int getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(int ageGroup) {
		this.ageGroup = ageGroup;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public List<Integer> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<Integer> symptoms) {
		this.symptoms = symptoms;
	}

	public List<Integer> getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(List<Integer> sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	
	
}
