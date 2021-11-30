package com.adhd.Olivia.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adhd.Olivia.enums.AgeGroup;
import com.adhd.Olivia.enums.Duration;
import com.adhd.Olivia.enums.Status;
import com.adhd.Olivia.models.db.Questionarrie;
import com.adhd.Olivia.models.db.User;
import com.adhd.Olivia.repo.QuestionarrieRepo;
import com.adhd.Olivia.repo.UserRepository;


@RestController
@RequestMapping("/questions")
public class QuestionarrieController {
	
	
	@Autowired
	public QuestionarrieRepo questionRepo;
	
	@Autowired
	public UserRepository userRepo;
	
	@PostMapping("/")
	public ResponseEntity<String> login(@RequestBody Map<String, Object> response){
		Questionarrie newAnswer = new Questionarrie();
		int ageGroup = (int) response.get("ageGroup");
		int status = (int) response.get("status");
		int userId = (int) response.get("userId");
		List<Integer> sleepTime = (List<Integer>) response.get("sleepTime");
		List<Integer> symptoms = (List<Integer>) response.get("symptoms");
		if(response.get("duration") == null) {
			newAnswer.setDuration(null);
		}else {
			int duration = (int) response.get("duration");
			newAnswer.setDuration(Duration.getById(duration));
		}		
		newAnswer.setAgeGroup(AgeGroup.getById(ageGroup));
		newAnswer.setStatus(Status.getById(status));
		User usr = userRepo.findById(userId);
		newAnswer.setUser(usr);
		newAnswer.setSymptoms(symptoms);
		newAnswer.setSleepTime(sleepTime);
		questionRepo.save(newAnswer);
		return new ResponseEntity<String>("OK",HttpStatus.CREATED);		
	}

}
