package com.adhd.Olivia.controllers;

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
import com.adhd.Olivia.models.response.QuestionarrieResp;
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
	public ResponseEntity<String> login(@RequestBody QuestionarrieResp response){
		Questionarrie newAnswer = new Questionarrie();
		newAnswer.setAgeGroup(AgeGroup.getById(response.getAgeGroup()));
		System.out.println(response.getStatus());
		newAnswer.setStatus(Status.getById(response.getStatus()));
		User usr = userRepo.findById(response.getUserId());
		newAnswer.setUser(usr);
		newAnswer.setSymptoms(response.getSymptoms());
		System.out.println(response.getDuration());
		newAnswer.setDuration(Duration.getById(response.getDuration()));
		newAnswer.setSleepTime(response.getSleepTime());
		questionRepo.save(newAnswer);
		return new ResponseEntity<String>("OK",HttpStatus.CREATED);		
	}

}
