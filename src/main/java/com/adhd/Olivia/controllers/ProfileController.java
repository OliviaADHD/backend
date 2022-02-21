package com.adhd.Olivia.controllers;


import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adhd.Olivia.custom.FileUploadUtil;
import com.adhd.Olivia.enums.Language;
import com.adhd.Olivia.models.db.Profile;
import com.adhd.Olivia.models.db.User;
import com.adhd.Olivia.repo.ProfileRepository;
import com.adhd.Olivia.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	
	@Autowired
	public UserRepository userRepo;
	
	
	@Autowired
	public ProfileRepository profileRepo;
	
	@PostMapping("/{userId}/photo")
	public ResponseEntity<String> uploadPhoto(@RequestPart("image") MultipartFile multipartFile,@PathVariable int userId){
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		User usr = userRepo.findById(userId);
		Optional<Profile> optProf = profileRepo.findByUser(usr);
		if(optProf.isEmpty()) {
			return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Profile prof = optProf.get();
		prof.setProfilePhoto(fileName);
		profileRepo.save(prof);
 
        String uploadDir = "user-photos/" + userId; 
        try {
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} catch (IOException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.OK);	
		}
		return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@PostMapping("/preferences")
	public ResponseEntity<String> preferencesSave(@RequestBody String json){
		ObjectMapper mapper = new ObjectMapper();		
		try {
			JsonNode actualObj = mapper.readTree(json);
			int language = actualObj.get("language").asInt();
			boolean darkMode = actualObj.get("darkMode").asBoolean();
			int userId = actualObj.get("userId").asInt();
			User usr = userRepo.findById(userId);
			Optional<Profile> optProf = profileRepo.findByUser(usr);
			if(optProf.isEmpty()) {
				return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			Profile prof = optProf.get();
			prof.setDarkMode(darkMode);
			prof.setLanguage(Language.getById(language));
			profileRepo.save(prof);
			return new ResponseEntity<String>("OK",HttpStatus.OK);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
				
	}
	
	@PostMapping("/privacy")
	public ResponseEntity<String> privacySave(@RequestBody String json){
		ObjectMapper mapper = new ObjectMapper();	
		try {
			JsonNode actualObj = mapper.readTree(json);
			boolean hidePhoto = actualObj.get("hidePhoto").asBoolean();
			boolean stopNotification = actualObj.get("stopNotification").asBoolean();
			int userId = actualObj.get("userId").asInt();
			User usr = userRepo.findById(userId);
			Optional<Profile> optProf = profileRepo.findByUser(usr);
			if(optProf.isEmpty()) {
				return new ResponseEntity<String>("User",HttpStatus.NOT_FOUND);
			}
			Profile prof = optProf.get();
			prof.setHidePhoto(hidePhoto);
			prof.setStopNotification(stopNotification);
			profileRepo.save(prof);
			return new ResponseEntity<String>("OK",HttpStatus.OK);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
	}
	

}
