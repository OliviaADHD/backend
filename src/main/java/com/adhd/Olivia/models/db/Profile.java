package com.adhd.Olivia.models.db;

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

import com.adhd.Olivia.enums.Language;

@Entity
public class Profile {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	
	@Column(columnDefinition = "boolean default false")
	private boolean darkMode;
	
	@Column(columnDefinition = "boolean default false")
	private boolean hidePhoto;
	
	@Column(columnDefinition = "boolean default false")
	private boolean stopNotification;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
    private Language language = Language.ENGLISH;
	
	@Column(nullable = true, length = 64)
	private String profilePhoto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDarkMode() {
		return darkMode;
	}

	public void setDarkMode(boolean darkMode) {
		this.darkMode = darkMode;
	}

	public boolean isHidePhoto() {
		return hidePhoto;
	}

	public void setHidePhoto(boolean hidePhoto) {
		this.hidePhoto = hidePhoto;
	}

	public boolean isStopNotification() {
		return stopNotification;
	}

	public void setStopNotification(boolean stopNotification) {
		this.stopNotification = stopNotification;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}	
	
}
