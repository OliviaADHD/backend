package com.adhd.Olivia.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@RequestMapping("/profile")
public class ProfileController {

	@PostMapping("/preferences")
	public ResponseEntity<String> preferencesSave(@RequestBody String json){
		ObjectMapper mapper = new ObjectMapper();
		Map<Object, String> map = null;
		try {
			map = mapper.readValue(json, Map.class);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		try {
			String language = map.get("language");
			String userId =  map.get("userId");
			String darkMood = map.get("darkMood");
		}catch (NullPointerException e) {
			return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);	
		}		
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);		
	}
	
	@PostMapping("/privacy")
	public ResponseEntity<String> privacySave(@RequestBody String json){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = null;
		try {
			map = mapper.readValue(json, Map.class);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		try {
			String hidePhoto = map.get("hidePhoto");
			String userId =  map.get("userId");
			String stopNotification = map.get("darkMood");	
		}catch (NullPointerException e) {
			return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);	
	}
}
