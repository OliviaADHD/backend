package com.adhd.Olivia.models.db;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.adhd.Olivia.enums.AgeGroup;
import com.adhd.Olivia.enums.Status;
import com.adhd.Olivia.enums.Duration;

@Entity
public class Questionarrie {

	@Id
    private int id;
	
	@Enumerated(EnumType.ORDINAL)
    private AgeGroup ageGroup;
	
	@Enumerated(EnumType.ORDINAL)
    private Status status; 
	
	private String symptoms;
	
	@Enumerated(EnumType.ORDINAL)
	private Duration duration;
	
	private String sleepTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AgeGroup getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(AgeGroup ageGroup) {
		this.ageGroup = ageGroup;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(int symptoms[] ) {
		String parsedSymptoms = Arrays.toString(symptoms);
		parsedSymptoms = parsedSymptoms.substring(1);
		parsedSymptoms = parsedSymptoms.substring(0, parsedSymptoms.length() - 1);
		this.symptoms = parsedSymptoms;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public String getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime[] ) {
		String parsedSleepTime = Arrays.toString(sleepTime);
		parsedSleepTime = parsedSleepTime.substring(1);
		parsedSleepTime = parsedSleepTime.substring(0, parsedSleepTime.length() - 1);
		this.sleepTime = parsedSleepTime;
	}
	
	
}
