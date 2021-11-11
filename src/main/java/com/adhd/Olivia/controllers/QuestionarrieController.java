package com.adhd.Olivia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adhd.Olivia.models.db.Users;
import com.adhd.Olivia.models.response.Login;
import com.adhd.Olivia.repo.QuestionarrieRepo;
import com.adhd.Olivia.repo.UserRepository;

@RestController
@RequestMapping("/questions")
public class QuestionarrieController {
	
	
	@Autowired
	public QuestionarrieRepo questionRepo;
	
	@PostMapping("/")
	public ResponseEntity<String> login(@RequestBody Login signIn){
		System.out.println(signIn.getEmail());
		List<Users> logedInUser = userRepo.findByEmailAndPassword(signIn.getEmail(), signIn.getPassword());
		if(logedInUser.size()==1) {
			String response = "{ 'userId':"+logedInUser.get(0).getId()+", 'name':"+logedInUser.get(0).getFullName()+"}";
			return new ResponseEntity<String>(response,HttpStatus.OK);		
		}else {
			return new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
		}
		
	}

}
