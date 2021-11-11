package com.adhd.Olivia.models.db;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.StringUtils;

import com.adhd.Olivia.enums.AgeGroup;
import com.adhd.Olivia.enums.Status;
import com.adhd.Olivia.enums.Duration;

@Entity
public class Questionarrie {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	
	@Column(name = "age_group")
	@Enumerated(EnumType.ORDINAL)
    private AgeGroup ageGroup;
	
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
    private Status status;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
	
	private String symptoms;
	
	@Column(name = "duration")
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

	public void setSymptoms(List<Integer> symptoms) {
		String parsedSymptoms = StringUtils.join(symptoms, ",");
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

	public void setSleepTime(List<Integer> sleepTime) {
		String parsedSleepTime = StringUtils.join(sleepTime, ",");
		this.sleepTime = parsedSleepTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
